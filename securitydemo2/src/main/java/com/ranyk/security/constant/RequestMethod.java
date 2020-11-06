package com.ranyk.security.constant;

import lombok.Getter;

/**
 * ClassName:RequestMethod
 * Description:请求方法常量类
 *
 * @author ranyi
 * @date 2020-11-06 13:55
 * Version: V1.0
 */
@Getter
public enum RequestMethod {

    /**
     * POST 请求方法
     */
    POST("POST"),
    /**
     * GET请求方法
     */
    GET("GET"),
    /**
     * PUT请求方法
     */
    PUT("PUT"),
    /**
     * OPTIONS请求方法
     */
    OPTIONS("OPTIONS"),
    /**
     * DELETE请求方法
     */
    DELETE("DELETE");

    /**
     * 请求方法属性
     */
    private final String method;

    /**
     * 构造方法,构造一个指定方法属性到的请求方法对象
     * @param method 方法属性值
     */
    RequestMethod(String method) {
        this.method = method;
    }
}
