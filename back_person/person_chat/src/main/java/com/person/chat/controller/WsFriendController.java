package com.person.chat.controller;

import com.person.auth.service.BaseService;
import com.person.chat.network.bean.webSocket.WsFriendReq;
import com.person.chat.pojo.entity.ChatFriend;
import com.person.chat.service.ChatFriendService;
import com.person.chat.webSocket.FriendMessageService;
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


    @MessageMapping("/ws/chat")
    @ApiOperation(value = "发送聊天消息")
    public void handleChat(Principal principal, WsFriendReq friendReq) {
        try {
            String myUserId = friendReq.getMyUserId();
            String friendUserId = friendReq.getFriendUserId();

            ChatFriend chatFriend = new ChatFriend(friendUserId, myUserId, friendReq.getMsg());
            chatFriendService.insertChatFriend(chatFriend);

            friendMessageService.convertAndSendToUser(myUserId, friendUserId, "/queue/chat", chatFriend);
            friendMessageService.convertAndSendToUser(myUserId, friendUserId, "/queue/friend");

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