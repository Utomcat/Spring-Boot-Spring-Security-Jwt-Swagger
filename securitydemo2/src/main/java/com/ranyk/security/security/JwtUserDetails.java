package com.ranyk.security.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * ClassName:JwtUserDetails
 * Description:Jwt 用户详情模型
 *
 * @author ranyi
 * @date 2020-11-09 14:36
 * Version: V1.0
 */
public class JwtUserDetails extends User {

    public JwtUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public JwtUserDetails(String username,
                          String password,
                          boolean enabled,
                          boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
