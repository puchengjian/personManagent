package com.person.chat.dao;

import com.person.chat.network.bean.chatFriend.UpdateChatFriendMsgReq;
import com.person.chat.pojo.entity.ChatFriend;
import com.person.chat.pojo.vo.ChatFriendVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 16:23
 */
@Mapper
public interface ChatFriendMapper {

    List<ChatFriendVO> listChatFriendByUserId(@Param("myUserId") String myUserId);

    List<ChatFriendVO> listChatFriendByFriendUserId(@Param("friendUserId") String friendUserId);

    boolean findReadByFriendUserId(@Param("userId") String userId);

    ChatFriendVO findChatFriend(@Param("myUserId") String myUserId, @Param("friendUserId") String friendUserId);

    List<ChatFriend> listChatFriendMsg(@Param("myUserId") String myUserId, @Param("friendUserId") String friendUserId);

    Integer insertChatFriend(ChatFriend chatFriend);

    Integer updateChatFriendRead(UpdateChatFriendMsgReq req);

    Integer deleteChatFriend(@Param("myUserId") String myUserId,@Param("friendUserId") String friendUserId);

}
