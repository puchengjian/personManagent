package com.person.system.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/31 11:16
 */
@Data
public class FriendVO {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("好友姓名")
    private String userName;

    @ApiModelProperty("好友头像")
    private String photo;

    @ApiModelProperty("好友年龄")
    private Integer userAge;

    @ApiModelProperty("好友手机号")
    private String phone;

    @ApiModelProperty("好友邮箱")
    private String email;

    @Override
    public String toString() {

        return "好友信息[ " +
                " 主键: " + id +
                ",好友姓名: " + userName + ", 好友年龄: " + userAge +
                ",好友手机号: " + phone + "，好友邮箱: " + email +
                " ]";
    }

}
