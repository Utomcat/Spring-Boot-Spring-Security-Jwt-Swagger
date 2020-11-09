package com.ranyk.security.config;

import com.ranyk.security.constant.RequestURL;
import com.ranyk.security.security.JwtAuthenticationFilter;
import com.ranyk.security.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * ClassName:WebSecurityConfig <br/>
 * Description:Spring Security 配置类 <br/>
 * <p>
 * 注解解释:<br/>
 * EnableWebSecurity 启用web安全<br/>
 * EnableGlobalMethodSecurity 启用 Spring Security 全局方法,等同于 .xml 中 &lt;global-method-security&gt; 标签的支持
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
     * 自动注入 用户详情业务对象
     */
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;


    /**
     * 进行登录身份认证的配置
     *
     * @param auth AuthenticationManagerBuilder对象,用于创建AuthenticationManager的SecurityBuilder。<br/>
     *             允许轻松构建内存身份验证，<br/>
     *             LDAP身份验证，<br/>
     *             基于JDBC的身份验证，<br/>
     *             添加UserDetailsService 和 添加AuthenticationProvider。
     * @throws Exception 抛出所有异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //当需要自定义登录身份处理时,在此处进行自定义的身份认证组件
       auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
    }

    /**
     * 覆写请求验证的方法
     *
     * @param http HttpSecurity 对象
     * @throws Exception 抛出所有异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http//禁用 csrf,使用JWT
                .cors().and().csrf().disable()
                //对请求进行授权处理
                .authorizeRequests()
                //匹配跨域预检请求处理
                .antMatchers(HttpMethod.OPTIONS, RequestURL.ALL_REQUEST.getUrl()).permitAll()
                //匹配登录请求处理
                .antMatchers(RequestURL.LOGIN.getUrl()).permitAll()
                //匹配swagger请求处理
                .antMatchers(RequestURL.SWAGGER_HTML.getUrl()).permitAll()
                .antMatchers(RequestURL.SWAGGER_RESOURCES.getUrl()).permitAll()
                .antMatchers(RequestURL.V2_API_DOCS.getUrl()).permitAll()
                .antMatchers(RequestURL.WEBJARS.getUrl()).permitAll()
                //设置其他请求都需验证身份
                .anyRequest().authenticated();
        //在此处对自定义的登录过滤器的添加
        //http.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        //在此处对自定义的认证过滤器的添加
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        //退出登录处理器创建,其用来对退出进行处理
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }


    /**
     * 创建认证管理器,并注册进 Spring Bean 容器中
     *
     * @return AuthenticationManager对象, 由父类的 authenticationManager() 方法创建
     * @throws Exception 抛出所有异常
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
