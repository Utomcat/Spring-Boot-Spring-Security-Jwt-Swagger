package com.ranyk.security.constant;

import lombok.Getter;

/**
 * ClassName:ResponseContextType<br/>
 * Description:Response 响应内容类型枚举常量
 *
 * @author ranyi
 * @date 2020-11-06 11:04
 * Version: V1.0
 */
@Getter
public enum ResponseContextType {

    /**
     * application/json 类型
     */
    APPLICATION_JSON("application/json; charset=utf-8");

    /**
     * ContentType 属性
     */
    private final String contentType;

    /**
     * 构造方法
     * @param contentType contentType 属性值
     */
    ResponseContextType(String contentType) {
        this.contentType = contentType;
    }


}
