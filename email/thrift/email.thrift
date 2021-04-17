namespace java com.test.thrift.email
namespace py sendEmail.api

service EmailService{
    bool sendEmailMessage(1:string email,2:string message);
}