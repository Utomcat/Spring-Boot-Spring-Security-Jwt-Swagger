package com.ranyk.security.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * ClassName:JwtAuthenticatioToken<br/>
 * Description:对令牌信息的简单封装类，作为认证和授权的信任凭证
 *
 * @author ranyi
 * @date 2019-12-27 22:36
 * Version: V1.0
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {


    private static final long serialVersionUID = 1L;

    private String token;

    public JwtAuthenticationToken(Object principal, Object credentials){
        super(principal, credentials);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, String token){
        super(principal, credentials);
        this.token = token;
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token) {
        super(principal, credentials, authorities);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
