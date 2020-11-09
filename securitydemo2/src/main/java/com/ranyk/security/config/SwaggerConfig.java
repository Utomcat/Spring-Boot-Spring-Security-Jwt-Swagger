package com.ranyk.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

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

        //创建请求头参数
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();

        //创建请求头参数
        parameterBuilder
                //设置该参数的 name
                .name("Authorization")
                //设置该参数是否必填
                .required(false)
                //设置该参数的描述
                .description("令牌")
                //设置该参数是那种参数
                .parameterType("header")
                //设置该参数的数据类型
                .modelRef(new ModelRef("string"))
                //构建该参数
                .build();

        //将创建的参数添加进 List 集合中
        parameters.add(parameterBuilder.build());

        //创建 Docket 对象返回

        //构造一个 文档类型为 SWAGGER_2 的 Docket 对象
        return new Docket(DocumentationType.SWAGGER_2)
                //设置该 Docket 的Api 信息
                .apiInfo(getApiInfo())
                //启动 ApiSelectorBuilder
                .select()
                //设置 Api
                .apis(RequestHandlerSelectors.any())
                //设置 path
                .paths(PathSelectors.any())
                //构建 Docket
                .build()
                //设置 Docket 的全局操作参数
                .globalOperationParameters(parameters);
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
                //.termsOfServiceUrl("www.baidu.com")
                //更新API版本
                .version("V1.0 版本")
                //更新负责该API的联系人信息
                //.contact(new Contact("ranyk","www.baidu.com","1390851149@qq.com"))
                //更新API许可证信息
                //.license("许可证信息")
                //更新API许可网址
                //.licenseUrl("许可网址")
                //创建一个ApiInfo对象(通过调用 apiInfoBuilder.build()方法)
                .build();
    }

}
