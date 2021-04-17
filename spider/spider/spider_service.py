# 获取2009~2019年间的北京市平均房价
# 获取2014~2020北京市软件工程专业的平均工资
import requests
import re
import time
import pymongo
from thrift.server import TServer
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

from spider.api import SpiderService

request_headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                                 'Chrome/60.0.3100.0 Safari/537.36'}


class SpiderServiceHandler:

    # 连接数据库
    def savetodb_price(self, price):
        client = pymongo.MongoClient('192.168.49.135', 27017)
        db = client.test  # 数据库
        collection = db.price  # collection名，相当于数据库的表
        year = {
            "price": str(float(price)/1000)
        }
        collection.insert_one(year)
        collection = db.price2
        year = {
            "price": price
        }
        collection.insert_one(year)

    def savetodb_wage(self, wage):
        client = pymongo.MongoClient('192.168.49.135', 27017)
        db = client.test  # 数据库
        collection = db.wage  # collection名，相当于数据库的表
        year = {
            "wage": wage
        }
        collection.insert_one(year)

    def get_info_price(self, turl):
        wb_data = requests.get(turl, headers=request_headers)
        # XHR解析
        d = wb_data.json()
        data_list = d['result']
        # 防止出现空白数据
        if data_list[0]['data'] == '':
            result = data_list[1]['data']
        else:
            result = data_list[0]['data']
        # print("20" +"%02d" % number + ": ")
        self.savetodb_price(result)

    def get_info_wage(self, url):
        wb_data = requests.get(url, headers=request_headers).text
        # 正则表达式
        prices_form = re.compile(r'20.*?年：￥(.*?)K')
        prices_all = re.findall(prices_form, wb_data)
        prices_all.reverse()
        for price in prices_all:
            self.savetodb_wage(price)

    def SpiderAllUrls(self):
        price_urls = ['http://data.stats.gov.cn/search.htm?s=%E5%8C%97%E4%BA%AC20{' \
                      '}%E5%B9%B4%E5%B9%B3%E5%9D%87%E6%88%BF%E4%BB%B7&m=searchdata&db=csnd&p=0'.format(
            str("%02d" % number))
            for number in range(9, 19)]
        wage_url = "https://m.jobui.com/salary/beijing-ruanjiangongcheng/"
        # each url
        self.get_info_wage(wage_url)
        time.sleep(3)
        print("wage_ok")

        for url in price_urls:
            self.get_info_price(url)
            time.sleep(3)
        print("price_ok")


if __name__ == '__main__':
    serverSocket = TSocket.TServerSocket(host='127.0.0.1', port=1025)
    transportFactory = TTransport.TFramedTransportFactory()
    protocolFactory = TBinaryProtocol.TBinaryProtocolFactory()
    handler = SpiderServiceHandler()
    processor = SpiderService.Processor(handler);
    thriftServer = TServer.TSimpleServer(processor, serverSocket, transportFactory, protocolFactory)
    print("启动中")
    thriftServer.serve()