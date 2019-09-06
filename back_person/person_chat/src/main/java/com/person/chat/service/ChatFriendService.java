package com.person.chat.service;

import com.person.chat.network.bean.chatFriend.UpdateChatFriendMsgReq;
import com.person.chat.pojo.entity.ChatFriend;
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
    List<ChatFriendVO> listChatFriendByUserId(String myUserId);

    /**
     * 查询好友Id的好友列表
     */
    List<ChatFriendVO> listChatFriendByFriendUserId(String friendUserId);

    /**
     * 根据登录人Id和朋友Id查询信息状态
     */
    ChatFriendVO findChatFriend(String myUserId, String friendUserId);


    /**
     * 查询聊天记录
     */
    List<ChatFriend> listChatFriendMsg(String myUserId, String friendUserId);

    /**
     * 新增聊天信息
     */
    boolean insertChatFriend(ChatFriend chatFriend);

    /**
     * 修改读取状态
     */
    boolean updateChatFriendRead(UpdateChatFriendMsgReq req);

}
