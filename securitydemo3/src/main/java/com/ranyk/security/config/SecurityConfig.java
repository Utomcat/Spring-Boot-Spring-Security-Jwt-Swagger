package com.ranyk.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * ClassName:SecurityConfig
 * Description:Spring Security 配置类
 *
 * @author ranyi
 * @date 2020-11-04 10:38
 * Version: V1.0
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((authorize) -> authorize
                        .antMatchers("/css/**","/index").permitAll()
                        .antMatchers("/user/**").hasRole("USER"))
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login-error")
                );
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}
