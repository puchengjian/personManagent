package com.person.chat.network.bean.chatFriend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: pzy
 * @create: 2019/9/4 16:38
 */
@Data
@ApiModel("修改聊天记录状态")
public class UpdateChatFriendMsgReq {

    @ApiModelProperty("登录人Id")
    private String myUserId;

    @ApiModelProperty("朋友Id")
    private String friendUserId;

}
