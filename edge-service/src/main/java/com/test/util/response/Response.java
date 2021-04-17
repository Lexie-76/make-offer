package com.test.util.response;

import java.io.Serializable;

// 因为返回值是JSone格式，所以需要将所有的返回值均定义成 Response 类的对象，并借助Springboot的JSon的序列化生成JSon字符串
// 因为要序列化成JSon字符串(使用二进制协议<（>二进制序列化>)，所以要 implements Serializable
public class Response implements Serializable {

    // 封装(声明)常见的错误类型
    // 1001, 用户名或者密码错误
    public static final Response USERNAME_PASSWORD_INVALID = new Response("1001", "username or password invalid");
    // 1002, 电话号码或者邮件地址没写
    public static final Response EMAIL_REQUIRED = new Response("1002", "email is required");
    // 1003, 发送验证码错误
    public static final Response SEND_VERIFYCODE_FAILED = new Response("1003", "send verify code failed");
    // 1004, 验证码错误
    public static final Response VERIFY_CODE_INVALID = new Response("1004", "verifyCode invalid");
    // 0, 成功
    public static final Response SUCCESS = new Response();


    // 常见的需要：code、message
    private String code;
    private String message;

    // 为了子类继承，声明缺省默认的构造方法，对应返回成功的情况。
    public Response() {
        this.code = "0";
        this.message = "success";
    }
    // 声明构造方法
    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // 声明未知情况的异常发生时，返回的响应信息
    public static Response exception(Exception e) {
        // 比较好的方式，将整个的异常e都返回，获取详细的异常信息。更好的方法，完成异常拦截器。
        return new Response("9999", e.getMessage());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
