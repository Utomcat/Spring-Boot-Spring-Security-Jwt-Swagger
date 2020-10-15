package com.ranyk.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 * @author Louis
 * @date Nov 28, 2018
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // 配置允许跨域访问的路径
        registry.addMapping("/**")
                // 配置允许跨域访问的源
                .allowedOrigins("*")
                // 配置允许请求方法
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                // 配置预检间隔时间
                .maxAge(168000)
                // 配置允许头部设置
                .allowedHeaders("*")
                // 是否发送cookie
                .allowCredentials(true);
    }
}