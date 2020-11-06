package com.ranyk.security.vo;

import com.ranyk.security.constant.HttpStatus;
import com.ranyk.security.constant.ResponseMsg;
import lombok.Data;

/**
 * ClassName:HttpResult <br/>
 * Description: http 请求响应结果封装工具类
 *
 * @author ranyi
 * @date 2020-11-06 11:10
 * Version: V1.0
 */
@Data
public class HttpResult {

    /**
     * 返回的响应状态码,默认 200
     */
    private int code = HttpStatus.SC_OK;
    /**
     * 返回的信息,默认 success
     */
    private String msg = ResponseMsg.RESPONSE_SUCCESS.getMsg();
    /**
     * 返回的数据对象,默认 null
     */
    private Object data = null;

    /**
     * 默认请求失败响应结果封装方法,默认失败响应状态码为 500; 响应信息为 服务器异常,请联系管理员
     *
     * @return 响应结果封装对象
     */
    public static HttpResult error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"服务器异常,请联系管理员！");
    }

    /**
     * 指定响应失败信息的失败请求响应结果封装方法,请求响应失败的状态码为 500;响应信息为给定的信息
     *
     * @param msg 请求响应失败的信息
     * @return 响应结果封装对象
     */
    public static HttpResult error(String msg){
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR,msg);
    }

    /**
     * 指定状态码和状态信息的失败请求响应结果封装方法,请求响应状态码为 给定的请状态码;请求响应消息为 给定的请求响应信息;
     *
     * @param code 请求响应码
     * @param msg 请求响应信息
     * @return 返回指定的失败请求响应结果封装对象
     */
    public static HttpResult error(int code, String msg) {
        HttpResult result = new HttpResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 给定响应成功信息的成功请求响应封装方法,请求响应状态码为 200;请求消息为给定的信息;
     *
     * @param msg 给定到的请求响应消息
     * @return 返回请求成功的响应结果封装对象
     */
    public static HttpResult ok(String msg){
        HttpResult result = new HttpResult();
        result.setMsg(msg);
        return result;
    }

    /**
     * 给定响应成功返回数据的成功请求响应封装方法,请求响应状态码为 200;请求消息为 success;返回数据为 给定的响应返回数据
     *
     * @param data 给定的请求响应返回数据
     * @return 返回请求成功的响应结果封装对象
     */
    public static HttpResult ok(Object data){
        HttpResult result = new HttpResult();
        result.setData(data);
        return result;
    }

}
