package com.person.friend.controller;

import com.person.auth.service.BaseService;
import com.person.auth.shiro.ShiroService;
import com.person.friend.networ.bean.webSocket.WsFriendReq;
import com.person.friend.pojo.entity.ChatFriend;
import com.person.friend.service.ChatFriendService;
import com.person.friend.webSocket.FriendMessageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * WebSocket 消息处理类
 * Created by sang on 2018/1/27.
 */
@Controller
@Slf4j
public class WsFriendController {

    @Autowired
    private FriendMessageService<ChatFriend> friendMessageService;
    @Autowired
    private ChatFriendService chatFriendService;
    @Autowired
    private BaseService baseService;


    @MessageMapping("/ws/friend/chat")
    @ApiOperation(value = "发送聊天消息")
    public void handleChat(Principal principal, WsFriendReq friendReq) {
        try {
            String myUserId = friendReq.getMyUserId();
            String friendUserId = friendReq.getFriendUserId();

            ChatFriend chatFriend = new ChatFriend(friendUserId, myUserId, friendReq.getMsg());
            chatFriendService.insertChatFriend(chatFriend);

            // 更新聊天消息数据
            friendMessageService.sendObjectToUser(myUserId, friendUserId, "/queue/friend/msg", chatFriend);
            // 更新好友聊天列表数据
            friendMessageService.sendListToUser(myUserId, friendUserId, "/queue/friend/chat/list");

            boolean result = chatFriendService.findReadByFriendUserId(myUserId);
            friendMessageService.sendBooleanToUser(myUserId, "/queue/read/friend", result);

        } catch (Exception e) {
            log.error("发送聊天信息异常:{}", e);
        }
    }

//    @MessageMapping("/ws/nf")
//    @SendTo("/topic/nf")
//    public String handleNF() {
//        return "系统消息";
//    }


}