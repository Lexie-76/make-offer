package com.test.util.randoncode;

import java.util.Random;

public class RC {
    // 获取字符集合s中size大小的随机字符串
    public static String randomCode(String s, int size) {
        StringBuilder result = new StringBuilder(size);

        Random random = new Random();
        for(int i=0;i<size;i++) {
            int loc = random.nextInt(s.length());
            result.append(s.charAt(loc));
        }
        return result.toString();
    }
}
