package com.test.thrift;

import com.test.thrift.email.EmailService;
import com.test.thrift.forecast.ForecastService;
import com.test.thrift.spider.SpiderService;
import com.test.thrift.user.UserService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceProvider {

    //spider
    @Value("${thrift.spider.ip}")
    private String spiderServerId;
    @Value("${thrift.spider.port}")
    private int spiderServerPort;

    //forecast
    @Value("${thrift.forecast.ip}")
    private String forecastServerId;
    @Value("${thrift.forecast.port}")
    private int forecastServerPort;

    //user
    @Value("${thrift.user.ip}")
    private String userServerIp;
    @Value("${thrift.user.port}")
    private int userServerPort;

    //email
    @Value("${thrift.email.ip}")
    private String emailServerIp;
    @Value("${thrift.email.port}")
    private int emailServerPort;

//用户登录和注册
    private enum ServiceType{
        USER,
        REGISTER
    }
    // 获取远程服务
    public <T> T getService(String ip, int port, ServiceType serverType){
        TSocket socket = new TSocket(ip, port, 3000);
        TTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);

        TServiceClient result = null;
        switch (serverType) {
            case USER :
                result = new UserService.Client(protocol);
                break;
            case REGISTER :
                result = new EmailService.Client(protocol);
                break;
        }
        // 强制类型转换为对应的泛型接口
        return (T)result;
    }

    // 获取用户服务 ThriftServer 的用户客户端
    public UserService.Client getUserService(){
        return getService(userServerIp, userServerPort, ServiceType.USER);
    }

    // 获取消息服务 Thrift Server 的消息客户端
    public EmailService.Client getEmailService(){
        return getService(emailServerIp, emailServerPort, ServiceType.REGISTER);
    }

    // 获取消息服务 Thrift Server 的爬虫客户端
    public SpiderService.Client getSpiderService(){
        TSocket socket = new TSocket(spiderServerId,spiderServerPort,60000);
        TTransport transport = new TFramedTransport(socket);
        try{
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);
        SpiderService.Client result = new SpiderService.Client(protocol);

        return result;
    }

    // 获取消息服务 Thrift Server 的预测客户端
    public ForecastService.Client getForecastService(){
        TSocket socket = new TSocket(forecastServerId,forecastServerPort,60000);
        TTransport transport = new TFramedTransport(socket);
        try{
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
            return null;
        }
        TProtocol protocol = new TBinaryProtocol(transport);
        ForecastService.Client result = new ForecastService.Client(protocol);
        return result;
    }
}
