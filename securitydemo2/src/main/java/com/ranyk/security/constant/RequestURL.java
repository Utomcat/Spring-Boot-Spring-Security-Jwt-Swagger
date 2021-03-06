package com.ranyk.security.constant;

import lombok.Getter;

/**
 * ClassName:RequestURL<br/>
 * Description:请求URL路径枚举常量
 *
 * @author ranyi
 * @date 2020-11-04 16:55
 * Version: V1.0
 */
@Getter
public enum RequestURL {

    /**
     * 所有请求 /**
     */
    ALL_REQUEST("/**"),
    /**
     * 登录请求 /login
     */
    LOGIN("/login"),
    /**
     * swagger页面请求  /swagger-ui.html
     */
    SWAGGER_HTML("/swagger-ui.html"),
    /**
     * swagger资源请求 /swagger-resources/**
     */
    SWAGGER_RESOURCES("/swagger-resources/**"),
    /**
     * swagger文档请求 /v2/api-docs"
     */
    V2_API_DOCS("/v2/api-docs"),
    /**
     * swagger UI请求 /webjars ** **
     */
    WEBJARS("/webjars/**/**");

    /**
     * 请求变量
     */
    private final String url;

    /**
     * 构造方法
     *
     * @param url 传入请求 URL 值
     */
    RequestURL(String url) {
        this.url = url;
    }

}
