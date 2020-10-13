package com.ranyk.security.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * ClassName:GrantedAuthorityImpl<br/>
 * Description:权限封装类
 *
 * @author ranyi
 * @date 2019-12-27 22:33
 * Version: V1.0
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return this.authority;
    }
}
