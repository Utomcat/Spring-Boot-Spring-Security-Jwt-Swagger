# `Security Demo 3` 项目

[TOC]

## 一、项目目的
> 初步搭建 `Spring Security` 框架的使用环境和初步进行有关配置,熟悉最初的 `Spring Security` 的配置方式和有关的流程

## 二、项目的搭建流程

### 1）、创建一个 `Spring Boot` 的 `Maven` 项目
> 过程略,自行百度

### 2）、导入有关 `maven` 依赖

- 有关的依赖如下

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
	<name>Spring Security Demo 3</name>
	<description>Spring Security Demo 3 project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
			<version>2.3.5.RELEASE</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-springsecurity5 -->
		<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity5</artifactId>
			<version>3.0.4.RELEASE</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
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
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
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

### 3）、创建 `Spring Security` 的配置类,对其进行有关配置

- `SecurityConfig.java` 类

```java

package com.ranyk.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { 

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests((authorize) -> authorize.antMatchers("/css/**","/index").permitAll()
                    .antMatchers("/user/**").hasRole("USER")
            .formLogin((formLogin) -> formLogin.loginPage("/login").failureUrl("/login-error"))
            );
    }
    
    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                     .username("user")
                     .password("password")
                     .roles("USER")
                     .build();
        return new InMemoryUserDetailsManager(userDetails);  
    }

}

```
- 解析
    - 配置类需要使用类注解 `@EnableWebSecurity` ,用于启用Web安全, 在该注解底层中还使用了 `@Configuration` 、 `@EnableGlobalAuthentication`,故不用再单独使用这两个注解
    - 方法 `protected void configure(HttpSecurity http) throws Exception` 的作用是对前端发起的请求进行一个安全验证
        - 方法 `authorizeRequests()` (授权请求)是对请求的安全验证
            - 内部的方法 `antMatchers()` (授权匹配器)是对需要验证的请求进行验证,其后是对其验证的处理
                - 方法 `permitAll()` (允许所有)是对其前面的请求验证的处理 ==> 全部通过验证处理
                - 方法 `hasRole()` (有角色)是对其前面的请求角色验证 ==> 判断是否拥有指定的角色
            - 内部方法 `loginPage()` (登录页面)是设置登录请求
            - 内部方法 `failureUrl()` (失败URL)是设置登录失败的请求
    - 方法 `protected UserDetailsService userDetailsService()` 的作用是用来创建一个用户信息的
        - 该方法必须使用注解 `@Bean` 目的是将其装配进容器,否则创建的用户则不会被使用,仍然使用 `Spring Security` 的默认用户,从而导致用户登录后因没有指定的角色,而出现 `403` 的错误
        - 方法 `User.withDefaultPasswordEncoder()` 用于创建 `UserBuilder` 对象
        - 方法 `username()` 用于设置创建的 `UserDetails` 对象的用户名(即登录的用户名)
        - 方法 `password()` 用于设置创建的 `UserDetails` 对象的密码(即登录的密码)
        - 方法 `roles()` 用于设置创建的 `UserDetails` 对象的角色(即登录的用户角色)
        - 方法 `build()` 用于设置创建的 `UserDetails` 对象
        - 构造方法 `InMemoryUserDetailsManager(userDetails)` 用于在内存中构造一个用户(个人理解为 类似于一个内存中的数据库)

### 4）、创建请求控制器 `Controller`

- 创建 `MainController.java` 

```java
package com.ranyk.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
```

### 5）、创建前端界面

#### Ⅰ、创建 `CSS` 样式
> 在 `Spring Boot` 项目中, `css` 样式是静态资源文件,在未自定义声明(即自己指定静态资源存放的位置)的情况下,会使用默认到的位置(即在 `resources/static/` 文件夹下)

- 在默认的静态资源文件夹下创建 `css` 文件夹 及 `main.css` 文件,`CSS` 样式如下:

```css
/**
 * @ClassName:main
 * @Description: css 样式
 * @author: ranyi
 * @date: 2020-11-04 10:52
 * Version: V1.0
 */
body {
    font-family: sans, serif;
    font-size: 1em;
}

p.error {
    font-weight: bold;
    color: red;
}

div.logout {
    float: right;
}
```

#### Ⅱ、创建 `HTML` 界面
> 在 `Spring Boot` 项目中, `HTML` 是页面模板,在未自定义声明(解释同上)的情况下,会使用默认的位置(即在 `resources/templates/` 文件夹下)

- 在 `templates` 文件夹下创建如下文件/文件夹:
    - `index.html`
    - `login.html`
    - `user/index.html`

- `index.html`

```html
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
    <head>
        <meta charset="UTF-8">
        <title>Hello Spring Security</title>
        <link rel="stylesheet" href="/css/main.css" th:herf="@{/css/main.css}" />
    </head>
    <body>
        <div th:fragment="logout" class="logout" sec:authorize="isAuthenticated()">
            Logged in user: <span sec:authentication="name"></span> |
            Roles: <span sec:authentication="principal.authorities"></span>
            <div>
                <form action="#" th:action="@{/logout}" method="post">
                    <input type="submit" value="Logout">
                </form>
            </div>
        </div>
        <h1>Hello Spring Security</h1>
        <p>This is an unsecured page, but you can access the secured pages after authenticating.</p>
        <ul>
            <li>Go to the <a href="/user/index" th:href="@{/user/index}">secured pages</a></li>
        </ul>
    </body>
</html>
```

- `login.html`

```html
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="https://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Login page</title>
        <link rel="stylesheet" href="/css/main.css" th:href="@{/css/main.css}" />
    </head>
    <body>
        <h1>Login page</h1>
        <p>Example user: user / password</p>
        <p th:if="${loginError}" class="error">Wrong user or password</p>
        <form th:action="@{/login}" method="post">
            <label for="username">Username</label>:
            <input type="text" id="username" name="username" autofocus="autofocus" /> <br/>
            <label for="password">Password</label>:
            <input type="password" id="password" name="password" /> <br/>
            <input type="submit" value="Log in">
        </form>
        <p><a href="/index" th:href="@{/index}">Back to home page</a></p>
    </body>
</html>
```

- `user/index.html`

```html
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="https://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Hello Spring Security</title>
        <link rel="stylesheet" href="/css/main.css" th:href="@{/css/main.css}" />
    </head>
    <body>
        <div th:substituteby="index::logout"></div>
        <h1>This is a secured page!</h1>
        <p><a href="/index" th:href="@{/index}">Back to home page</a></p>
    </body>
</html>
```



