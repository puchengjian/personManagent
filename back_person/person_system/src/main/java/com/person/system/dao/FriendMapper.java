package com.person.system.dao;

import com.person.system.network.bean.friend.ListFriendReq;
import com.person.system.pojo.entity.Friend;
import com.person.system.pojo.vo.FriendVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 10:44
 */
@Mapper
public interface FriendMapper {

    List<FriendVO> listFriend(ListFriendReq req);

    Integer countFriend(ListFriendReq req);

    Friend findFriendByFriendUserId(@Param("myUserId") String myUserId, @Param("friendUserId") String friendUserId);

    Integer insertFriend(Friend friend);

    Integer deleteFriend(@Param("myUserId") String myUserId, @Param("friendId") String friendId);


}
