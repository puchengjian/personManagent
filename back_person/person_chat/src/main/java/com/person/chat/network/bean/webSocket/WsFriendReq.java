package com.person.chat.network.bean.webSocket;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/9/3 18:08
 */
@Data
@ApiModel("好友聊天webSocket请求参数")
public class WsFriendReq {

    @ApiModelProperty("消息内容")
    private String msg;

    @ApiModelProperty("好友Id")
    private String friendUserId;

    @ApiModelProperty("登录人Id")
    private String myUserId;

    @Override
    public String toString() {

        return "聊天信息:[ 好友Id: " + friendUserId + ", 登录人Id" + myUserId + ", 消息内容" + msg + "]";
    }

}
