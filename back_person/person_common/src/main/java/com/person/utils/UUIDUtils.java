package com.person.utils;

import java.util.UUID;

/**
 * @author: pzy
 * @create: 2019/8/8 16:01
 */
public class UUIDUtils {

    /**
     * 获取UUID 转换大写
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replaceAll("-", "");

        return str.toUpperCase();
    }

}
