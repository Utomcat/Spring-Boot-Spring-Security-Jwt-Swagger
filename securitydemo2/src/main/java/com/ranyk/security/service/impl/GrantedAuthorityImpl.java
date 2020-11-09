package com.ranyk.security.service.impl;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * ClassName:GrantedAuthorityImpl
 * Description:
 *
 * @author ranyi
 * @date 2020-11-06 16:07
 * Version: V1.0
 */
@Setter
public class GrantedAuthorityImpl implements GrantedAuthority {


    private static final long serialVersionUID = 1L;

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return this.authority;
    }
}
