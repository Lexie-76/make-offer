namespace java com.test.thrift.forecast
namespace py forecast.api

service ForecastService{
    double time_responce(1:i32 k, 2:double a, 3:double u, 4:double x0);
    void house_price();
    void monthly_pay();
}