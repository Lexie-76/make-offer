package com.test.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(value = "com.test.user.mapper") // 用来将 MyBatis 中的 Mapper 加载到 Springboot 项目中
public class UserServiceApplication {
    public static void main(String[] args) {
        // 启动 Springboot 的应用，参数是当前类的 class
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

