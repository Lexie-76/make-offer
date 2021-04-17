package com.test.util.response;

//继承 Response，传入登录成功的用户的token，以便后续使用（借助此token找到redis缓存中对应的用户）
//作为登录成功的返回信息
public class LoginResponse extends Response {
    //定义登录成功用户的token
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
