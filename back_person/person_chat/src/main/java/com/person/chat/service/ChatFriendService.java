package com.person.chat.service;

import com.person.chat.pojo.vo.ChatFriendVO;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 16:26
 */
public interface ChatFriendService {

    /**
     * 查询当前登录人的好友列表
     */
    List<ChatFriendVO> listChatFriend(String myUserId);

}
