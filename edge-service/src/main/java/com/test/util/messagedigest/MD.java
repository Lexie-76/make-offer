package com.test.util.messagedigest;

import org.apache.tomcat.util.buf.HexUtils;

import java.security.MessageDigest;

public class MD {
    // 实现对密码进行MD5
    public static String md5(String password) {
        try {
            // 使用JDK自带的MD5方法，生成一个md5类型的信息摘要对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 使用MD5摘要用户密码， 使用utf8编码，返回byte数组
            byte[] md5Bytes = md5.digest(password.getBytes("utf-8"));
            // 调用HexUtils工具类，转成十六进制串返回
            return HexUtils.toHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
