package com.ranyk.security.service.impl;

import com.ranyk.security.model.User;
import com.ranyk.security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName:SysUserServiceImpl
 * Description:
 *
 * @author ranyi
 * @date 2020-10-13 0:16
 * Version: V1.0
 */
@Service
public class SysUserServiceImpl implements UserService {
    @Override
    public User findByUsername(String username) {
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        String password = new BCryptPasswordEncoder().encode("123");
        user.setPassword(password);
        return user;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("sys:user:view");
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        permissions.add("sys:user:delete");
        return permissions;
    }
}
