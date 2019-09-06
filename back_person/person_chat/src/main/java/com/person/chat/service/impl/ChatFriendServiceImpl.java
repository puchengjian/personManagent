package com.person.chat.service.impl;

import com.person.auth.shiro.ShiroService;
import com.person.chat.dao.ChatFriendMapper;
import com.person.chat.network.bean.chatFriend.UpdateChatFriendMsgReq;
import com.person.chat.pojo.entity.ChatFriend;
import com.person.chat.pojo.vo.ChatFriendVO;
import com.person.chat.service.ChatFriendService;
import com.person.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 16:26
 */
@Service
@Slf4j
public class ChatFriendServiceImpl implements ChatFriendService {

    @Autowired
    private ChatFriendMapper chatFriendMapper;


    @Override
    public List<ChatFriendVO> listChatFriendByUserId(String myUserId) {
        return chatFriendMapper.listChatFriendByUserId(myUserId);
    }

    @Override
    public List<ChatFriendVO> listChatFriendByFriendUserId(String friendUserId) {
        return chatFriendMapper.listChatFriendByFriendUserId(friendUserId);
    }

    @Override
    public ChatFriendVO findChatFriend(String myUserId, String friendUserId) {
        return chatFriendMapper.findChatFriend(myUserId, friendUserId);
    }


    @Override
    public List<ChatFriend> listChatFriendMsg(String myUserId, String friendUserId) {
        return chatFriendMapper.listChatFriendMsg(myUserId, friendUserId);
    }

    @Override
    public boolean insertChatFriend(ChatFriend chatFriend) {
        chatFriend.setId(UUIDUtils.getUUID());

        return chatFriendMapper.insertChatFriend(chatFriend) > 0;
    }

    @Override
    public boolean updateChatFriendRead(UpdateChatFriendMsgReq req) {
        return chatFriendMapper.updateChatFriendRead(req) > 0;
    }


}
