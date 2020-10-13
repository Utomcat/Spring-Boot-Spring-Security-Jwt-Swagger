package com.ranyk.security.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Component;

/**
 * ClassName:LoginBean
 * Description:
 *
 * @author ranyi
 * @date 2020-10-13 0:23
 * Version: V1.0
 */
@Data
@ToString
@Component
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "登录对象类",description = "对登录的对象进行封装")
public class LoginBean {

    @ApiModelProperty(name = "account",value = "登录用户名",dataType = "java.lang.String")
    private String userName;

    @ApiModelProperty(name = "password",value = "登录密码",dataType = "java.lang.String")
    private String password;

    @ApiModelProperty(name = "captcha",value = "登录验证码",dataType = "java.lang.String")
    private String captcha;

}
