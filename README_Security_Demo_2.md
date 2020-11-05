#  `Security Demo 2` 项目

[TOC]


## 一、项目结构

```html
-- src 
    -- main 
        -- java 
            -- com 
                -- ranyk 
                    -- security 
                        -- Security2Application.java  <!-- 项目启动类 -->

        -- resources 
            -- static <!-- 项目静态资源存放文件夹 -->
            -- templates <!-- 项目页面资源存放文件夹 -->
            -- application.yml <!-- 项目配置文件 -->
            -- application-dev.yml <!-- 项目多环境配置文件之开发环境配置文件 -->
            -- application-prod.yml <!-- 项目多环境配置文件之正式环境配置文件 -->
            -- application-test.yml <!-- 项目多环境配置文件之测试环境配置文件 -->

    -- test 
        -- java 
            -- com 
                -- ranyk 
                    -- security        
                        -- Security2ApplicationTests.java <!-- 项目测试类 --> 
```

## 二、项目环境集成

> 以下集成顺序可以调换

### 1）、集成 `Swagger` 

#### Ⅰ、导入 `maven` 依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.5.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.ranyk</groupId>
	<artifactId>security</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Spring Security Demo 2</name>
	<description>Security Demo 2 project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>

		<!-- swagger -->
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.9.2</version>
		</dependency>
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.9.2</version>
		</dependency>

		<!--Spring Web-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<!--Spring Boot 热部署-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>

		<!--Spring Boot 配置处理器-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>

		<!--Lombok-->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

		<!--Spring Boot 单元测试-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```

#### Ⅱ、 创建 `Swagger` 的配置类 `SwaggerConfig.java` 

```java
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

```

### 2）、集成 `Spring Security` 

#### Ⅰ、导入 `maven` 依赖

```xml
<dependencies>
    <!--Spring Security-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- fastjson -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.74</version>
    </dependency>
</dependencies>
```

#### Ⅱ、 创建 `Spring Security` 的配置类 `WebSecurityConfig.java` 
> `Spring Security` 的配置类通常是通过 _**继承 `WebSecurityConfigurerAdapter.java` 抽象类**_ 或 _**实现 `类名不详` 接口**_

- `WebSecurityConfig.java` 

```java
package com.ranyk.security.config;

import com.ranyk.security.constant.RequestURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自动注入 用户详情业务对象
     */
    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * 进行登录身份认证的配置
     *
     * @param auth  AuthenticationManagerBuilder对象,用于创建AuthenticationManager的SecurityBuilder。<br/>
     *              允许轻松构建内存身份验证，<br/>
     *              LDAP身份验证，<br/>
     *              基于JDBC的身份验证，<br/>
     *              添加UserDetailsService 和 添加AuthenticationProvider。
     * @throws Exception 抛出所有异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //当需要自定义登录身份处理时,在此处进行自定义的身份认证组件
        super.configure(auth);
    }

    /**
     * 覆写请求验证的方法
     *
     * @param http HttpSecurity 对象
     * @throws Exception 抛出所有异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //对请求进行授权处理
        http.authorizeRequests((authorize) -> authorize
                //匹配跨域预检请求处理
                .antMatchers(HttpMethod.OPTIONS, RequestURL.ALL_REQUEST.getUrl()).permitAll()
                //匹配登录请求处理
                .antMatchers(RequestURL.LOGIN.getUrl()).permitAll()
                //匹配swagger请求处理
                .antMatchers(RequestURL.SWAGGER_HTML.getUrl(),
                        RequestURL.SWAGGER_RESOURCES.getUrl(),
                        RequestURL.V2_API_DOCS.getUrl(),
                        RequestURL.WEBJARS.getUrl()).permitAll()
                //设置其他请求都需验证身份
                .anyRequest().authenticated())
                //禁用 csrf,使用JWT
                .cors().and().csrf().disable()
                //在此处对登录认证的过滤器和访问控制是登录检查状态过滤器的添加
                //退出登录处理器创建,其用来对退出进行处理
                .logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }


    /**
     * 创建认证管理器,并注册进 Spring Bean 容器中
     *
     * @return AuthenticationManager对象,由父类的 authenticationManager() 方法创建
     * @throws Exception 抛出所有异常
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
```

> 配置类中的目的是配置http请求的验证以及对请求的授权处理


#### Ⅲ、创建登录认证触发过滤器类 `JwtLoginFilter.java`




ⅣⅤⅥⅦ