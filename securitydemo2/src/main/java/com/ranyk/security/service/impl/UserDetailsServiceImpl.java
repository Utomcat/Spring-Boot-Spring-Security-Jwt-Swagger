package com.ranyk.security.service.impl;

import com.ranyk.security.entity.User;
import com.ranyk.security.security.JwtUserDetails;
import com.ranyk.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName:UserDetailsServiceImpl<br\>
 * Description:用户详情业务实现类
 *
 * @author ranyi
 * @date 2020-11-09 14:27
 * Version: V1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserService userService;


    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //获取指定用户名的用户
        User user = userService.findUserByUserName(username);

        //判断用户是否存在
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }

        Set<String> permissions = userService.findPermissions(username);
        List<GrantedAuthorityImpl> grantedAuthorities  = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());

        return new JwtUserDetails(username,user.getPassword(),grantedAuthorities);
    }
}
