package com.test.controller;

import com.test.redis.RedisClient;
import com.test.thrift.ServiceProvider;
import com.test.thrift.email.EmailService;
import com.test.thrift.user.UserInfo;
import com.test.thrift.user.UserService;
import com.test.thrift.user.dto.UserDTO;
import com.test.util.conversion.ConvertDTO;
import com.test.util.messagedigest.MD;
import com.test.util.randoncode.RC;
import com.test.util.response.LoginResponse;
import com.test.util.response.Response;
import com.test.util.token.Token;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/makeoffer/user")
public class UserController {


    @Resource       // 注入 ThriftClient
    private ServiceProvider serviceProvider;

    //借助 redis client 实现单点登录
    @Resource
    private RedisClient redisClient;

    // 04. 登录页面跳转
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }



    // 01. 登录功能  url = /user/login, method = post
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody       // 返回的数据是 Json 数据
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password){
        // 1. 验证用户名和密码
        // 1-1. 从数据库中查询username的用户信息
        // 使用 ServiceProvider 中的用户服务，调用其中的方法，获取指定 username 的用户信息
        UserService.Iface userService = serviceProvider.getUserService();
        UserInfo userInfo = null;
        try {
            userInfo = userService.getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;      // Thrift 网络出错
        }
        // 1-2. 对用户进行校验
        if (userInfo == null){
            return Response.USERNAME_PASSWORD_INVALID;      // 没有 username 的用户
        }
        if (!userInfo.getPassword().equalsIgnoreCase(MD.md5(password))){
            return Response.USERNAME_PASSWORD_INVALID;      // password 出错
        }
        // 以上完成了用户验证，并且保证用户正确的登录

        // 2. 将用户登录信息放入 redis 保存
        // 2-1. 生成存入 redis 的 key, 随机生成不重复 token
        String token = Token.genToken();        // 随机生成 32 位的字符串
        // 2-2. 将 token、userInfo(提取需要的字段->UserDTO) 作为 key、value 存入 redis
        UserDTO userDTO = ConvertDTO.toUserDTO(userInfo);
        redisClient.set(token, userDTO, 3000);

        // 返回登录成功的响应
        return new LoginResponse(token);
    }

    // 02-0. 发送验证码
    // url : /user/sendVerifyCode, method : post
    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.POST)
    @ResponseBody       // 返回Json字符串数据
    public Response sendVerifyCode(@RequestParam(value = "email") String email){
        // 1. 生成验证码
        String message = "VerifyCode is : ";
        String code = RC.randomCode("0123456789", 6);

        EmailService.Iface registerService = serviceProvider.getEmailService();
        try {
            boolean result = false;
            if (StringUtils.isNotBlank(email)) {
                // 使用 email 发送验证码
                result = registerService.sendEmailMessage(email, message + code);
                // 将验证码放入 redis
                redisClient.set(email, code);
            } else {
                return Response.EMAIL_REQUIRED;
            }
            if (!result){
                return Response.SEND_VERIFYCODE_FAILED;
            }
        } catch (TException e){
            e.printStackTrace();
            return Response.exception(e);
        }
        // 发送成功
        return  Response.SUCCESS;
    }
    // 02. 注册功能
    // 参数：必须：username, password, verifyCode   email
    // url - /user/register, method - post

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody       // 返回Json字符串数据
    public Response register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("email")String email,
                             @RequestParam("verifyCode") String verifyCode){


        // 2. 邮箱接收验证码
        String redisCode = redisClient.get(email);
        if (!verifyCode.equals(redisCode)){
            return Response.VERIFY_CODE_INVALID;
        }

        //3 册数据校验成功，生成实体类
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(MD.md5(password));
        userInfo.setEmail(email);

        // 4. 获取 user-thrift-service 的 UserService
        UserService.Iface userService = serviceProvider.getUserService();
        try {
            userService.registerUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }

        return Response.SUCCESS;
    }

    // 03. 实现单点登录的审核功能，从 Redis 中以 token 为 key 获取一个对应的用户信息
    // url : /user/authentication, method : post
    // 返回： Use人DTO 的 Json 字符串的形式
    @RequestMapping(value = "authentication", method = RequestMethod.POST)
    @ResponseBody
    public UserDTO authentication(String token){
        // 从 Redis 去 token 对应的 value
        return redisClient.get(token);
    }
}
