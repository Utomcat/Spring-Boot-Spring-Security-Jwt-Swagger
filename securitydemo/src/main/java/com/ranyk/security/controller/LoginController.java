package com.ranyk.security.controller;

import com.ranyk.security.security.JwtAuthenticationToken;
import com.ranyk.security.utils.SecurityUtils;
import com.ranyk.security.vo.HttpResult;
import com.ranyk.security.vo.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ClassName:LoginController<br/>
 * Description:登录控制器
 *
 * @author ranyi
 * @date 2020-10-12 21:16
 * Version: V1.0
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    public HttpResult login(LoginBean loginBean, HttpServletRequest request) throws IOException {

        String username = loginBean.getUserName();
        String password = loginBean.getPassword();

        JwtAuthenticationToken token = SecurityUtils.login(request,username,password,authenticationManager);

        return HttpResult.ok(token);

    }

}
