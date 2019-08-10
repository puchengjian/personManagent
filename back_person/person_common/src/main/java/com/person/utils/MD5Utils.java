package com.person.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * md5加密工具类
 *
 * @author: pzy
 * @create: 2019/8/5 16:25
 */
@Slf4j
public class MD5Utils {

    public MD5Utils() {

    }

    public static final String SALT = "beam";

    public static final String ALGORITH_NAME = "md5";

    private static final int HASH_ITERATIONS = 3;

    public static String encrypt(String password) {
        return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
    }

    public static String encrypt(String userName, String password) {
        return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(userName + SALT), HASH_ITERATIONS).toHex();
    }


}
