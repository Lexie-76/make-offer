import numpy as np
from scipy.optimize import curve_fit

##传统模型检验
def traditional_model(x, y, data, data_forecast):
    data_h = np.array(data_forecast[0:len(data)]).T
    sum_h = data_h.sum()
    mean_h = sum_h/len(data)
    S1 = np.sum((data_h-mean_h)**2)/len(data)
    ## 残差方差
    e = data - data_h
    sum_e = e.sum()
    mean_e = sum_e/len(data)
    S2 = np.sum((e-mean_e)**2)/len(data)
    ## 后验差比
    C = S2/S1
    print(C)
    ## 结果
    if (C <= 0.35):
        print('1级，效果好')
    elif (C <= 0.5 and C >= 0.35):
        print('2级，效果合格')
    elif (C <= 0.65 and C >= 0.5):
        print('3级，效果勉强')
    else:
        print('4级，效果不合格')

##二次多项式拟合及后验差比
def square_exp(x,y,data):
    z = np.polyfit(x, y, 2)
    exp = np.poly1d(z)
    data_forecast = exp(x)
    ##预测结果方差
    data_h = np.array(data_forecast[0:len(data)]).T
    sum_h = data_h.sum()
    mean_h = sum_h/len(data)
    S1 = np.sum((data_h-mean_h)**2)/len(data)
    ## 残差方差
    e = data - data_h
    sum_e = e.sum()
    mean_e = sum_e/len(data)
    S2 = np.sum((e-mean_e)**2)/len(data)
    ## 后验差比
    C = S2/S1
    print(C)

##三次多项式拟合及后验差比
def cube_exp(x,y,data):
    z = np.polyfit(x, y, 3)
    exp = np.poly1d(z)
    data_forecast = exp(x)
    ##预测结果方差
    data_h = np.array(data_forecast[0:len(data)]).T
    sum_h = data_h.sum()
    mean_h = sum_h/len(data)
    S1 = np.sum((data_h-mean_h)**2)/len(data)
    ## 残差方差
    e = data - data_h
    sum_e = e.sum()
    mean_e = sum_e/len(data)
    S2 = np.sum((e-mean_e)**2)/len(data)
    ## 后验差比
    C = S2/S1
    print(C)

##四次多项式拟合及后验差比
def fourth_power_exp(x,y,data):
    z = np.polyfit(x, y, 4)
    exp = np.poly1d(z)
    data_forecast = exp(x)
    ##预测结果方差
    data_h = np.array(data_forecast[0:len(data)]).T
    sum_h = data_h.sum()
    mean_h = sum_h/len(data)
    S1 = np.sum((data_h-mean_h)**2)/len(data)
    ## 残差方差
    e = data - data_h
    sum_e = e.sum()
    mean_e = sum_e/len(data)
    S2 = np.sum((e-mean_e)**2)/len(data)
    ## 后验差比
    C = S2/S1
    print(C)

##五次多项式拟合及后验差比
def fifth_power_exp(x,y,data):
    z = np.polyfit(x, y, 4)
    exp = np.poly1d(z)
    data_forecast = exp(x)
    ##预测结果方差
    data_h = np.array(data_forecast[0:len(data)]).T
    sum_h = data_h.sum()
    mean_h = sum_h/len(data)
    S1 = np.sum((data_h-mean_h)**2)/len(data)
    ## 残差方差
    e = data - data_h
    sum_e = e.sum()
    mean_e = sum_e/len(data)
    S2 = np.sum((e-mean_e)**2)/len(data)
    ## 后验差比
    C = S2/S1
    print(C)

##指数拟合及后验差比
def exponential_exp(x,y,data):
    def func(x,a,b):
        return a*np.exp(b/x)
    popt, pcov = curve_fit(func, x, y)
    a=popt[0]#popt里面是拟合系数，读者可以自己help其用法
    b=popt[1]
    data_forecast=func(x, a, b)
    #print(data_forecast)
    data_h = np.array(data_forecast[0:len(data)]).T
    sum_h = data_h.sum()
    mean_h = sum_h/len(data)
    S1 = np.sum((data_h-mean_h)**2)/len(data)
    ## 残差方差
    e = data - data_h
    sum_e = e.sum()
    mean_e = sum_e/len(data)
    S2 = np.sum((e-mean_e)**2)/len(data)
    ## 后验差比
    C = S2/S1
    print(C)

##对数拟合及后验差比
def logarithmic_exp(x,y,data):
    def func(x, a, b):
        y = a * np.log(x) + b
        return y
    popt, pcov = curve_fit(func, x, y)
    yhat = func(x ,popt[0] ,popt[1] )
    print(yhat)
    data_h = np.array(yhat[0:len(data)]).T
    sum_h = data_h.sum()
    mean_h = sum_h / len(data)
    S1 = np.sum((data_h - mean_h) ** 2) / len(data)
    ## 残差方差
    e = data - data_h
    sum_e = e.sum()
    mean_e = sum_e / len(data)
    S2 = np.sum((e - mean_e) ** 2) / len(data)
    ## 后验差比
    C = S2 / S1
    print(C)




