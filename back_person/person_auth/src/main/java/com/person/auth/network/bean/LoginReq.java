package com.person.auth.network.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/5 16:21
 */
@Data
@ApiModel("登录请求参数")
public class LoginReq {

    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String password;

}
