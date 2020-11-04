package com.ranyk.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * ClassName:WebSecurityConfig <br/>
 * Description:Spring Security 配置类 <br/>
 *
 * 注解解释:
 *  Configuration 注解为标识当前类是一个配置类 <br/>
 *  EnableWebSecurity <br/>
 *  EnableGlobalMethodSecurity 启用 Spring Security 全局方法,等同于 .xml 中 &lt;global-method-security&gt; 标签的支持
 *
 * @author ranyi
 * @date 2020-11-03 17:21
 * Version: V1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {



    }
}
