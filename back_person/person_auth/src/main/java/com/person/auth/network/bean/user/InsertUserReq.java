package com.person.auth.network.bean.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/8 16:08
 */
@ApiModel("新增用户参数类")
@Data
public class InsertUserReq {

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("角色Id")
    private String roleId;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("年龄")
    private Integer userAge;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("图片")
    private String photo;

    @ApiModelProperty("邮箱")
    private String email;

    @Override
    public String toString() {
        String builder = "用户信息[ " +
                " 主键: " + id +
                ",姓名: " + userName + "，年龄: " + userAge + ", 手机号: " + phone +
                ",账号: " + account + "，密码: " + password + ", 邮箱: " + email +
                " ]";

        return builder;
    }

}
