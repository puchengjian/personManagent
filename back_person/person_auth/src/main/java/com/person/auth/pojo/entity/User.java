package com.person.auth.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: pzy
 * @create: 2019/8/5 15:08
 */
@Data
@ApiModel("用户数据")
public class User implements Serializable {
    private static final long serialVersionUID = -2901796419987813464L;

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("账号")
    private String account;

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

    @ApiModelProperty("true-启用 状态 1-启用 0-禁用 ")
    private boolean enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Override
    public String toString() {
        String builder = "用户信息[ " +
                " 主键: " + id +
                ",姓名: " + userName + "，年龄: " + userAge + ", 手机号: " + phone +
                ",账号: " + account + "，密码: " + password + ", 邮箱: " + email +
                ",状态: " + enabled + "，创建时间: " + createTime + ", 更新时间: " + updateTime +
                " ]";

        return builder;
    }

}
