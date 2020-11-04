package com.ranyk.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ClassName:SwaggerConfig
 * Description:swagger配置类
 *
 * @author ranyi
 * @date 2020-11-03 16:32
 * Version: V1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    /**
     * 创建 Springfox框架的 API 接口构建器,并注册成 Bean
     *
     * @return Docket 对象
     */
    @Bean
    public Docket createRestApi(){

        //创建 Docket 对象返回
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 获取 ApiInfo 对象,构建一个 Swagger Api 的信息对象
     *
     * @return ApiInfo 对象
     */
    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                //更新API标题
                .title("Swagger Api Title")
                //更新API描述
                .description("Swagger Api 描述")
                //更新网址服务条款
                .termsOfServiceUrl("www.baidu.com")
                //更新API版本
                .version("V1.0 版本")
                //更新负责该API的联系人信息
                .contact(new Contact("ranyk","www.baidu.com","1390851149@qq.com"))
                //更新API许可证信息
                .license("许可证信息")
                //更新API许可网址
                .licenseUrl("许可网址")
                //创建一个ApiInfo对象(通过调用 apiInfoBuilder.build()方法)
                .build();
    }

}
