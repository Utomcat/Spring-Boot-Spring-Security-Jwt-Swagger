package com.ranyk.security.service;

import com.ranyk.security.model.User;

import java.util.Set;

/**
 * ClassName:UserService
 * Description:
 *
 * @author ranyi
 * @date 2020-10-13 0:15
 * Version: V1.0
 */
public interface UserService {

    User findByUsername(String username);


    Set<String> findPermissions(String username);


}
