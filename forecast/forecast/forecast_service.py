import numpy as np
import pymongo
import pandas as pd
from thrift.server import TServer
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

from forecast.api import ForecastService


class ForecastServiceHandler:

#响应时间序列
    def time_responce(self, k, a, u, x0):
        c = u / a
        return (x0 - c) * (np.e ** (-a * k)) + c

#房价预测
    def house_price(self):
    ##打开mongodb取数据
        client = pymongo.MongoClient(host='192.168.49.135', port=27017)
        db = client.test
        collection = db.price2
        data = collection.find()
        data = list(data)
        price_data = []
        for i in data:
            price_data.append(i['price'])
        price_0 = []
        for i in price_data:
            price_0.append(float(i))

    ##传统GM(1,1)模型
        #数列累加得到x1
        price_1 = np.cumsum(price_0)
        #求B和U
        y = []
        Z = []
        for i in range(1, len(price_0)):
            y.append(price_0[i])
            Z.append(-1/2*(price_1[i-1]+price_1[i]))
        B = np.array(Z).reshape(len(price_0) - 1, 1)
        one = np.ones(len(price_0) - 1)
        B = np.c_[B, one]
        Y = np.array(y).reshape(len(price_0) - 1, 1)
        a, u = np.dot(np.dot(np.linalg.inv(np.dot(B.T, B)), B.T), Y)
        #计算初始预测值
        price_sum_forecast = []  # 累加预测值
        price_initial_forecast = []  # 原始预测值
        x0 = price_0[0]
        price_sum_forecast.append(self.time_responce(0, a, u, x0))
        price_initial_forecast.append(price_sum_forecast[0])
        for i in range(1, len(price_0)+12):  # 预测2019-2030
            price_sum_forecast.append(self.time_responce(i, a, u, x0))
            price_initial_forecast.append(price_sum_forecast[i]-price_sum_forecast[i-1])

    ##二次拟合改进
        x=[i for i in range(1, len(price_initial_forecast)+1)]
        y=[]
        for i in price_initial_forecast:
            y.append(i[0])
        z = np.polyfit(x, y, 2)
        exp = np.poly1d(z)
        #print(exp)
        price_improve1_forecast = exp(x)
        #print(price_improve1_forecast)

    ##马尔可夫修正
        #残差序列
        price_forecast_section = price_improve1_forecast[0:len(price_0)]
        residual = []
        for i in range(1, len(price_0)):
            residual.append((price_forecast_section[i]-price_0[i])/price_0[i])
        #print(residual)
        #状态及边界
        status = []
        edge_1 = 0
        edge_2 = 0
        edge_3 = 0
        edge_4 = 0
        E2_num = len(price_0)//2
        E1_num = (len(price_0)-1-E2_num)//2
        E3_num = len(price_0)-1-E2_num-E1_num
        for i in range(0, len(price_0)-1):
            status.append(0)
        li = np.array(residual)
        out = np.argsort(-li)
        edge_1 = residual[out[0]]
        edge_4 = residual[out[-1]]
        for i in out:
            if E1_num > 0:
                status[i] = 1
                E1_num = E1_num-1
                edge_2 = residual[i]
            elif E2_num > 0:
                status[i] = 2
                E2_num = E2_num-1
                edge_3 = residual[i]
            else:
                status[i] = 3
        #一步转移矩阵
        p1 = np.zeros(9).reshape(3, 3)
        for i in range(0, 3):
            for j in range(0, 3):
                for k in range(0, len(status)-1):
                    if status[k] == i+1 and status[k+1] == j+1:
                        p1[i][j] = p1[i][j]+1
        p1_sum = p1.sum(axis=1)
        for i in range(0, 3):
            p1[i]=p1[i]/p1_sum[i]
        #二步转移矩阵
        p1 = np.array(p1)
        p2 = np.dot(p1, p1)
        #三步转移矩阵
        p2 = np.array(p2)
        p3 = np.dot(p1, p2)
        #预测矩阵及状态影响修正
        three_step = 0
        two_step = 0
        one_step = 0
        for i in range(1, len(status)):
            if three_step == 0:
                three_step = status[i*(-1)]
            elif two_step == 0 and status[i*(-1)] != three_step:
                two_step = status[i*(-1)]
                break
        for i in range(1, 4):
            if i != three_step and i != two_step:
                one_step = i

        p_E1 = p1[one_step-1][0]+p2[two_step-1][0]+p3[three_step-1][0]
        p_E2 = p1[one_step-1][1]+p2[two_step-1][1]+p3[three_step-1][1]
        p_E3 = p1[one_step-1][2]+p2[two_step-1][2]+p3[three_step-1][2]

        if p_E1 > p_E2 and p_E1 > p_E3:
            price_final_forecast = price_improve1_forecast*(1-(edge_1+edge_2)/2)
        elif p_E2 > p_E1 and p_E2 > p_E3:
            price_final_forecast = price_improve1_forecast*(1-(edge_2+edge_3)/2)
        elif p_E3 > p_E1 and p_E3 > p_E3:
            price_final_forecast = price_improve1_forecast*(1-(edge_3+edge_4)/2)

        price_final_forecast = price_final_forecast[-10::]
        price_final_forecast = price_final_forecast/1000
        future_year = [i for i in range(2021, 2031)]

    ##将预测值存入mongodb
        collection = db.houseforecast
        for i in range(0, 10):
            year = {
                "year": future_year[i],
                "price": price_final_forecast[i]
            }
            collection.insert_one(year)
        print("数据保存成功")

#工资预测
    def monthly_pay(self):
        ##打开mongodb取数据
        client = pymongo.MongoClient(host='192.168.49.135', port=27017)
        db = client.test
        collection = db.wage
        data = collection.find()
        data = list(data)
        wage_data = []
        for i in data:
            wage_data.append(i['wage'])
        wage_0 = []
        for i in wage_data:
            wage_0.append(float(i))

        ##传统GM(1,1)模型
        #数列累加得到x1
        price_1 = np.cumsum(wage_0)
        #求B和U
        y = []
        Z = []
        for i in range(1, len(wage_0)):
            y.append(wage_0[i])
            Z.append(-1/2*(price_1[i-1]+price_1[i]))
        B = np.array(Z).reshape(len(wage_0) - 1, 1)
        one = np.ones(len(wage_0) - 1)
        B = np.c_[B, one]
        Y = np.array(y).reshape(len(wage_0) - 1, 1)
        a, u = np.dot(np.dot(np.linalg.inv(np.dot(B.T, B)), B.T), Y)
        #计算初始预测值
        price_sum_forecast = []  # 累加预测值
        price_initial_forecast = []  # 原始预测值
        price_sum_forecast.append(self.time_responce(0, a, u, wage_0[0]))
        price_initial_forecast.append(price_sum_forecast[0])
        for i in range(1, len(wage_0)+10):  # 预测2021-2030
            price_sum_forecast.append(self.time_responce(i, a, u, wage_0[0]))
            price_initial_forecast.append(price_sum_forecast[i]-price_sum_forecast[i-1])

        ##马尔可夫修正
        #残差序列
        price_forecast_section = price_initial_forecast[0:len(wage_0)]
        residual = []
        for i in range(1, len(wage_0)):
            residual.append((price_forecast_section[i]-wage_0[i])/wage_0[i])
        #print(residual)
        #状态及边界
        status=[]
        edge_1=0
        edge_2=0
        edge_3=0
        edge_4=0
        E2_num = len(wage_0)//2
        E1_num = (len(wage_0)-1-E2_num)//2
        E3_num = len(wage_0)-1-E2_num-E1_num
        for i in range(0, len(wage_0)-1):
            status.append(0)
        residual_wage = []
        for i in residual:
            residual_wage.append(i[0])
        li = np.array(residual_wage)
        out = np.argsort(-li)
        edge_1 = residual[out[0]]
        edge_4 = residual[out[-1]]
        for i in out:
            if E1_num > 0:
                status[i] = 1
                E1_num = E1_num-1
                edge_2 = residual[i]
            elif E2_num > 0:
                status[i] = 2
                E2_num = E2_num-1
                edge_3 = residual[i]
            else:
                status[i] = 3
        #一步转移矩阵
        p1 = np.zeros(9).reshape(3, 3)
        for i in range(0, 3):
            for j in range(0, 3):
                for k in range(0, len(status)-1):
                    if status[k] == i+1 and status[k+1] == j+1:
                        p1[i][j] = p1[i][j]+1
        p1_sum = p1.sum(axis=1)
        for i in range(0, 3):
            p1[i]=p1[i]/p1_sum[i]
        #二步转移矩阵
        p1 = np.array(p1)
        p2 = np.dot(p1, p1)
        #三步转移矩阵
        p2 = np.array(p2)
        p3 = np.dot(p2, p1)
        #预测矩阵及状态影响修正
        three_step = 0
        two_step = 0
        one_step = 0
        for i in range(1, len(status)):
            if three_step == 0:
                three_step = status[i*(-1)]
            elif two_step == 0 and status[i*(-1)] != three_step:
                two_step = status[i*(-1)]
                break
        for i in range(1, 4):
            if i != three_step and i != two_step:
                one_step = i

        p_E1 = p1[one_step-1][0]+p2[two_step-1][0]+p3[three_step-1][0]
        p_E2 = p1[one_step-1][1]+p2[two_step-1][1]+p3[three_step-1][1]
        p_E3 = p1[one_step-1][2]+p2[two_step-1][2]+p3[three_step-1][2]

        if p_E1 > p_E2 and p_E1 > p_E3:
            price_final_forecast = price_initial_forecast*(1-(edge_1+edge_2)/2)
        elif p_E2 > p_E1 and p_E2 > p_E3:
            price_final_forecast = price_initial_forecast*(1-(edge_2+edge_3)/2)
        elif p_E3 > p_E1 and p_E3 > p_E3:
            price_final_forecast = price_initial_forecast*(1-(edge_3+edge_4)/2)

        price_final_forecast = price_final_forecast[-10::]
        future_year = [i for i in range(2021, 2031)]

        ##将预测值存入mongodb
        collection = db.wageforecast
        for i in range(0, 10):
            year = {
                "year": future_year[i],
                "wage": price_final_forecast[i][0]
            }
            collection.insert_one(year)
        print("数据保存成功")

if __name__ == '__main__':
    serverSocket = TSocket.TServerSocket(host='127.0.0.1', port=9090)
    transportFactory = TTransport.TFramedTransportFactory()
    protocolFactory = TBinaryProtocol.TBinaryProtocolFactory()
    handler = ForecastServiceHandler()
    processor = ForecastService.Processor(handler);
    thriftServer = TServer.TSimpleServer(processor, serverSocket, transportFactory, protocolFactory)
    print("启动中")
    thriftServer.serve()