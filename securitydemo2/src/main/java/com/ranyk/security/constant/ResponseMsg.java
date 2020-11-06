package com.ranyk.security.constant;

import lombok.Getter;

/**
 * ClassName:ResponseMsg<br/>
 * Description:请求响应信息常量类
 *
 * @author ranyi
 * @date 2020-11-06 12:42
 * Version: V1.0
 */
@Getter
public enum ResponseMsg {

    /**
     * 响应成功返回信息  success
     */
    RESPONSE_SUCCESS("success");

    /**
     * 响应信息属性
     */
    private final String msg;

    /**
     * 构造方法
     * @param msg 响应信息属性值
     */
    ResponseMsg(String msg) {
        this.msg = msg;
    }
}
