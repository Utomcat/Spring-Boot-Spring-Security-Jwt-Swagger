package com.ranyk.security.service;

import com.ranyk.security.entity.User;

import java.util.Set;

/**
 * ClassName:UserService
 * Description: 用户业务接口
 *
 * @author ranyi
 * @date 2020-11-09 14:28
 * Version: V1.0
 */
public interface UserService {

    /**
     * 查找用户,通过用户名
     * @param userName 查找的条件 -- 用户名
     * @return 返回查询到的结果
     */
    User findUserByUserName(String userName);

    /**
     * 查找用户的菜单权限标识集合,通过用户名
     * @param userName 用户名
     * @return 返回查到的该用户的权限 Set 集合
     */
    Set<String> findPermissions(String userName);

}
