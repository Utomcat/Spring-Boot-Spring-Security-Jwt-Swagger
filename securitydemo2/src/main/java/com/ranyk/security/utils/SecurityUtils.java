package com.ranyk.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:SecurityUtils<br/>
 * Description: Spring Security 工具类
 *
 * @author ranyi
 * @date 2020-11-06 10:24
 * Version: V1.0
 */
public class SecurityUtils {

    /**
     * 从 Security Context 中获取认证对象,再从中获取 用户名参数
     *
     * @return 返回从 Authentication 对象中获得的用户名参数
     */
    public static String getUserName() {

        //没有提供认证对象,则从 SecurityContextHolder 中得到的 Context 中获得 Authentication 对象
        Authentication authentication = getAuthentication();

        //调用从 Authentication 中获得用户名参数的方法
        return getUserName(authentication);

    }

    /**
     * 方法重载,提供一个认证对象,从认证对象中拿到对应的用户名参数
     *
     * @param authentication 认证对象
     * @return 返回获得的用户名参数
     */
    public static String getUserName(Authentication authentication) {

        String userName = null;

        if (null != authentication) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();
            }
        }

        return userName;

    }

    /**
     * 从 SecurityContextHolder 中得到的 Context 中获得 Authentication 对象
     *
     * @return 返回获得的 Authentication 对象
     */
    public static Authentication getAuthentication() {

        SecurityContext context = SecurityContextHolder.getContext();

        if (null == context) {
            return null;
        }

        Authentication authentication = context.getAuthentication();
        return authentication;

    }

    /**
     * 对令牌认证的操作方法
     * @param request 请求对象
     */
    public static void checkAuthentication(HttpServletRequest request) {
        //从请求对象 HTTPServletRequest 对象中获得 token 令牌,再从token 中获得 认证对象 authentication
        Authentication authentication = JwtTokenUtils.getAuthenticationFromToken(request);
        //设置 Context 对象中 authentication 属性值
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
