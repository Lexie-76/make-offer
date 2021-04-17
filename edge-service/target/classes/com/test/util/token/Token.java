package com.test.util.token;


import com.test.util.randoncode.RC;

public class Token {
    // 生成Token，需要考虑 2 个问题：
    // 1. 生成的基础：包括什么字符（数字、字母、大小写）
    // 2. 长度：生成多长的字符串
    public static String genToken() {
        // 指定生成基础、长度
        return RC.randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }
}
