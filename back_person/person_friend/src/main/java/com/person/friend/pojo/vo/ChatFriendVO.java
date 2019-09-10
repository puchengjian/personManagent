package com.person.friend.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/8/31 16:26
 */
@Data
@ApiModel("好友列表VO")
public class ChatFriendVO {

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("好友姓名")
    private String userName;

    @ApiModelProperty("头像")
    private String photo;

    @ApiModelProperty("是否已读消息")
    private boolean read;


    @Override
    public String toString() {

        return "好友列表信息[ " +
                " 主键: " + id +
                ",好友姓名: " + userName + ", 头像: " + photo +
                ",是否有未读消息: " + read +
                " ]";
    }

}
