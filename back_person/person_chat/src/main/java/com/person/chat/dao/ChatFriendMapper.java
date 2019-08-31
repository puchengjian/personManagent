package com.person.chat.dao;

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

    List<ChatFriendVO> listChatFriend(@Param("myUserId") String myUserId);

}
