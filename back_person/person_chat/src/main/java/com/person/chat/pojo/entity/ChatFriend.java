package com.person.chat.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: pzy
 * @create: 2019/8/31 16:18
 */
@Data
@ApiModel("好友聊天信息")
public class ChatFriend {

    @ApiModelProperty("主键Id")
    private String id;

    @ApiModelProperty("登录人Id")
    private String myUserId;

    @ApiModelProperty("好友Id")
    private String friendUserId;

    @ApiModelProperty("信息")
    private String msg;

    @ApiModelProperty("是否已经查看")
    private boolean read;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    public ChatFriend(){}

    public ChatFriend(String friendUserId, String myUserId, String msg) {
        this.friendUserId = friendUserId;
        this.myUserId = myUserId;
        this.msg = msg;
    }

    @Override
    public String toString() {

        return "聊天信息[ " +
                " 主键: " + id +
                ",登录人Id: " + myUserId + "，好友Id: " + friendUserId +
                ",信息: " + msg + "，是否已经查看: " + read + ", 发送时间: " + createTime +
                " ]";
    }


}
