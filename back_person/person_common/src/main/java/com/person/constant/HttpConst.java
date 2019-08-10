package com.person.constant;

/**
 * @author: pzy
 * @create: 2019/8/5 15:49
 */
public class HttpConst {

    /**
     * 请求成功
     */
    public static final Integer SUCCESS = 200;

    /**
     * 客户端请求语法错误，服务端无法理解
     */
    public static final Integer BAD_REQUEST = 400;

    /**
     * 身份认证
     */
    public static final Integer UNAUTHORIZED = 401;

    /**
     * 服务器内部错误，无法完成请求
     */
    public static final Integer INTERNAL_SERVER_ERROR = 500;

    /**
     * 请求错误
     */
    public static final String ERROR_400 = "请求错误";

    /**
     * 服务器内部错误
     */
    public static final String ERROR_500 = "服务器繁忙~";

}
