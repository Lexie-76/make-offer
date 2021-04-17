namespace java com.test.thrift.spider
namespace py spider.api

service SpiderService{
    void SpiderAllUrls();
    void get_info_wage(1:string turl);
    void get_info_price(1:string turl);
    void savetodb_wage(1:i32 wage);
    void savetodb_price(1:i32 price);
}