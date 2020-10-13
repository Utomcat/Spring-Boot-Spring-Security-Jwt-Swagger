package com.ranyk.security.utils;

import com.ranyk.security.security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:SecurityUtils
 * Description:Security 相关操作的工具类
 *
 * @author ranyi
 * @date 2020-10-12 21:56
 * Version: V1.0
 */
public class SecurityUtils {


    public static JwtAuthenticationToken login(HttpServletRequest request, String userName, String password, AuthenticationManager authenticationManager){

        JwtAuthenticationToken token = new JwtAuthenticationToken(userName, password);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        Authentication authenticate = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        token.setToken(JwtTokenUtils.generateToken(authenticate));

        return token;

    }

    public static void checkAuthentication(HttpServletRequest request){

        Authentication authentication = JwtTokenUtils.getAuthenticationeFromToken(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }


    public static String getUserName() {
        String username = null;
        Authentication authentication = getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    public static String getUserName(Authentication authentication) {
        String username = null;
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }


    public static Authentication getAuthentication() {
        if(SecurityContextHolder.getContext() == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

}
