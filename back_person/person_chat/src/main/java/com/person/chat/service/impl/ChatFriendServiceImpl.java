package com.person.chat.service.impl;

import com.person.chat.dao.ChatFriendMapper;
import com.person.chat.pojo.vo.ChatFriendVO;
import com.person.chat.service.ChatFriendService;
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
    public List<ChatFriendVO> listChatFriend(String myUserId) {
        return chatFriendMapper.listChatFriend(myUserId);
    }


}
