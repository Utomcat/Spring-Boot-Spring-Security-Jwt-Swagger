package com.ranyk.security.controller;


import com.ranyk.security.security.JwtAuthenticatioToken;
import com.ranyk.security.utils.SecurityUtils;
import com.ranyk.security.vo.HttpResult;
import com.ranyk.security.vo.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录控制器
 * @author Louis
 * @date Nov 28, 2018
 */
@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * 登录接口
	 */
	@PostMapping(value = "/login")
	public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
		String username = loginBean.getUsername();
		String password = loginBean.getPassword();
		
		// 系统登录认证
		JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
				
		return HttpResult.ok(token);
	}

}
