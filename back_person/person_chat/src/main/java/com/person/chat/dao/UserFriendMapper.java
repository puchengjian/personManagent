package com.person.chat.dao;

import com.person.chat.network.bean.friend.ListFriendReq;
import com.person.auth.pojo.entity.UserFriend;
import com.person.chat.pojo.vo.FriendVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 10:44
 */
@Mapper
public interface UserFriendMapper {

    List<FriendVO> listFriend(ListFriendReq req);

    Integer countFriend(ListFriendReq req);

    UserFriend findFriendByFriendUserId(@Param("myUserId") String myUserId, @Param("friendUserId") String friendUserId);

    Integer insertFriend(UserFriend userFriend);


}
