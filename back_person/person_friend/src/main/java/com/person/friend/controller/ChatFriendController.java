package com.person.friend.controller;

import com.person.auth.shiro.ShiroService;
import com.person.friend.networ.bean.chatFriend.UpdateChatFriendMsgReq;
import com.person.friend.pojo.entity.ChatFriend;
import com.person.friend.pojo.vo.ChatFriendVO;
import com.person.friend.service.ChatFriendService;
import com.person.friend.webSocket.FriendMessageService;
import com.person.json.SuccessOrFailure;
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
@RequestMapping("/api/person/v1/friend")
@Slf4j
public class ChatFriendController {

    @Autowired
    private ChatFriendService chatFriendService;
    @Autowired
    private FriendMessageService friendMessageService;


    @GetMapping("/chat/friend")
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

    @GetMapping("/read/friend")
    @ApiOperation(value = "查询当前登录人是否有未读消息", response = boolean.class)
    SuccessOrFailure findReadByFriendUserId() {
        try {
            boolean result = chatFriendService.findReadByFriendUserId(ShiroService.getId());
            friendMessageService.sendBooleanToUser(ShiroService.getId(), "/queue/read/friend", result);

            return SuccessOrFailure.SUCCESS(result);
        } catch (Exception e) {
            log.error("查询当前登录人是否有未读消息异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/msg/friend")
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

    @PutMapping("/chat/read/friend")
    @ApiOperation(value = "修改聊天记录状态 (已读状态)")
    SuccessOrFailure updateChatFriendMsg(@RequestBody UpdateChatFriendMsgReq req) {
        boolean flag = false;
        try {
            String myUserId = req.getMyUserId();
            String friendUserId = req.getFriendUserId();

            // 根据登录人Id和朋友Id查询是否有未读消息
            ChatFriendVO chatFriend = chatFriendService.findChatFriend(myUserId, friendUserId);
            // 当前登录人，并且有未读消息
            if (chatFriend != null && !chatFriend.isRead()) {
                flag = true;
            } else { // 没有未读消息
                chatFriend = chatFriendService.findChatFriend(friendUserId, myUserId);
                // 有未读消息
                if (chatFriend != null && !chatFriend.isRead())
                    flag = true;
            }

            // 更新消息状态
            if (flag) {
                chatFriendService.updateChatFriendRead(req);
                req.setMyUserId(friendUserId);
                req.setFriendUserId(myUserId);

                friendMessageService.sendListToUser(myUserId, "/queue/friend/chat/list");
                friendMessageService.sendObjectToUser(friendUserId, "/queue/friend/msg/read", req);
            }

            return SuccessOrFailure.SUCCESS;
        } catch (Exception e) {
            log.error("修改聊天记录状态异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

}
