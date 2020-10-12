package com.ranyk.security.config;

import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.*;
import java.io.IOException;

/**
 * ClassName:JwtAuthenticationFilter
 * Description:
 *
 * @author ranyi
 * @date 2020-10-11 15:03
 * Version: V1.0
 */
public class JwtAuthenticationFilter implements Filter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
