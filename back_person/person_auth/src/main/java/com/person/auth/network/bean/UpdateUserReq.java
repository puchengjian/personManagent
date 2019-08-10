package com.person.auth.network.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/8 16:10
 */
@ApiModel("修改用户参数类")
@Data
public class UpdateUserReq {

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("姓名")
    private String userName;

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
                ", 邮箱: " + email +
                " ]";

        return builder;
    }

}
