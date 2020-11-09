package com.ranyk.security.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ClassName:JwtAuthenticationProvider<br\>
 * Description:Jwt 方式认证的认证授权提供者
 *
 * @author ranyi
 * @date 2020-11-09 14:20
 * Version: V1.0
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 认证方法
     *
     * @param authentication 需要认证的 Authentication 对象
     * @return 返回认证后的 Authentication 对象
     * @throws AuthenticationException 认证授权异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // TODO 自定义的登录认证逻辑在此处添加

        return super.authenticate(authentication);
    }

    /**
     *
     *
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        //TODO 自定义密码的验证逻辑在此处添加

        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
