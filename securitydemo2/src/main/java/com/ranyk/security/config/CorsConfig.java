package com.ranyk.security.config;

import com.ranyk.security.constant.CorsRequestUrl;
import com.ranyk.security.constant.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName:CorsConfig<br/>
 * Description:跨域访问配置
 *
 * @author ranyi
 * @date 2020-11-06 13:37
 * Version: V1.0
 */
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 添加跨域请求映射
     *
     * @param registry 协助注册基于全局URL模式的CorsConfiguration映射对象
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        //对跨域访问请求的URL映射
        registry.addMapping(CorsRequestUrl.ALL_REQUEST.getRequest())
                //设置允许跨域访问的访问源
                .allowedOrigins(CorsRequestUrl.ALL_ORIGINS.getRequest())
                //设置允许访问的请求方法
                .allowedMethods(RequestMethod.PUT.getMethod(),
                                RequestMethod.GET.getMethod(),
                                RequestMethod.POST.getMethod(),
                                RequestMethod.OPTIONS.getMethod(),
                                RequestMethod.DELETE.getMethod())
                //设置预检间隔时间,单位 s;默认 1800 s(30 min)
                .maxAge(168000)
                //设置允许请求头部设置参数
                .allowedHeaders("*")
                //设置是否发送 cookie
                .allowCredentials(true);
    }
}
