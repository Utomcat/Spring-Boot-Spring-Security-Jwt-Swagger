package com.ranyk.security.constant;

import lombok.Getter;

/**
 * ClassName:CorsRequstUrl
 * Description:跨域访问请求URL常量类
 *
 * @author ranyi
 * @date 2020-11-06 13:42
 * Version: V1.0
 */
@Getter
public enum  CorsRequestUrl {

    /**
     * 所有请求 /**
     */
    ALL_REQUEST("/**"),
    /**
     * 所有跨域访问源
     */
    ALL_ORIGINS("*");


    private final String request;


    CorsRequestUrl(String request) {
        this.request = request;
    }
}
