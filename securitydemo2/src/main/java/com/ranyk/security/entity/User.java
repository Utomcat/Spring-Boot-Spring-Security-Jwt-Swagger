package com.ranyk.security.entity;

import lombok.Data;

/**
 * ClassName:User<br/>
 * Description:用户对象实体类
 *
 * @author ranyi
 * @date 2020-11-09 14:29
 * Version: V1.0
 */
@Data
public class User {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 用户名
     */
    private  String userName;
    /**
     * 密码
     */
    private String password;

}
