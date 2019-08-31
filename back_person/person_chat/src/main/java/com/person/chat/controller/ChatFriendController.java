package com.person.chat.controller;

import com.person.chat.pojo.vo.ChatFriendVO;
import com.person.chat.service.ChatFriendService;
import com.person.json.SuccessOrFailure;
import com.person.auth.shiro.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ShiroService shiroService;


    @GetMapping("/friend")
    @ApiOperation(value = "查询好友聊天列表", response = ChatFriendVO.class)
    SuccessOrFailure listChatFriend() {
        try {
            List<ChatFriendVO> chatFriendVOS = chatFriendService.listChatFriend(shiroService.getId());

            return SuccessOrFailure.SUCCESS(chatFriendVOS);
        } catch (Exception e) {
            log.error("查询好友聊天列表异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

}
