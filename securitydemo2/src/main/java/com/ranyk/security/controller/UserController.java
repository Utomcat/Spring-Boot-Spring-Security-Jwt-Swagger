package com.ranyk.security.controller;

import com.ranyk.security.vo.HttpResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:UserController<br/>
 * Description:用户请求控制器
 *
 * @author ranyi
 * @date 2020-11-09 14:41
 * Version: V1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping(value="findAll")
    @PreAuthorize("hasAuthority('sys:user:view')")
    public HttpResult findAll() {
        return HttpResult.ok("the findAll service is called success.");
    }

    @GetMapping(value="edit")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public HttpResult edit() {
        return HttpResult.ok("the edit service is called success.");
    }

    @GetMapping(value="delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public HttpResult delete() {
        return HttpResult.ok("the delete service is called success.");
    }


}
