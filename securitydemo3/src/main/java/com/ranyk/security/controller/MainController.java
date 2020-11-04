package com.ranyk.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName:MainController
 * Description:请求分发处理器
 *
 * @author ranyi
 * @date 2020-11-04 10:39
 * Version: V1.0
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex(){
        return "user/index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";
    }

}
