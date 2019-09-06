package com.person.chat.controller;

import com.person.chat.network.bean.chatFriend.UpdateChatFriendMsgReq;
import com.person.chat.pojo.entity.ChatFriend;
import com.person.chat.pojo.vo.ChatFriendVO;
import com.person.chat.service.ChatFriendService;
import com.person.chat.webSocket.FriendMessageService;
import com.person.json.SuccessOrFailure;
import com.person.auth.shiro.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 16:27
 */
@RestController
@Api(description = "好友聊天信息管理")
@RequestMapping("/api/person/v1/chat")
@Slf4j
public class ChatFriendController {

    @Autowired
    private ChatFriendService chatFriendService;
    @Autowired
    private FriendMessageService friendMessageService;


    @GetMapping("/friend")
    @ApiOperation(value = "查询好友聊天列表", response = ChatFriendVO.class)
    SuccessOrFailure listChatFriend() {
        try {
            List<ChatFriendVO> chatFriendVOS = chatFriendService.listChatFriendByUserId(ShiroService.getId());

            return SuccessOrFailure.SUCCESS(chatFriendVOS);
        } catch (Exception e) {
            log.error("查询好友聊天列表异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/friend/msg")
    @ApiOperation(value = "查询好友聊天记录", response = ChatFriend.class)
    SuccessOrFailure listChatFriendMsg(@RequestParam String friendUserId) {
        try {
            List<ChatFriend> chatFriends = chatFriendService.listChatFriendMsg(ShiroService.getId(), friendUserId);

            return SuccessOrFailure.SUCCESS(chatFriends);
        } catch (Exception e) {
            log.error("查询好友聊天记录异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PutMapping("/friend")
    @ApiOperation(value = "修改聊天记录状态 (已读状态)")
    SuccessOrFailure updateChatFriendMsg(@RequestBody UpdateChatFriendMsgReq req) {
        boolean flag = false;
        try {
            String myUserId = req.getMyUserId();
            String friendUserId = req.getFriendUserId();

            ChatFriendVO chatFriend = chatFriendService.findChatFriend(myUserId, friendUserId);
            if (chatFriend != null && !chatFriend.isRead())
                flag = true;
            else {
                chatFriend = chatFriendService.findChatFriend(friendUserId, myUserId);
                if (chatFriend != null && !chatFriend.isRead())
                    flag = true;
            }


            if (flag) {
                chatFriendService.updateChatFriendRead(req);
                req.setMyUserId(friendUserId);
                req.setFriendUserId(myUserId);

                friendMessageService.convertAndSendToUser(myUserId, "/queue/friend");
                friendMessageService.convertAndSendToUser(friendUserId, "/queue/chat/read", req);
            }

            return SuccessOrFailure.SUCCESS;
        } catch (Exception e) {
            log.error("修改聊天记录状态异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

}
