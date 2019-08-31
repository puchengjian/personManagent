package com.person.system.service;

import com.person.system.network.bean.friend.ListFriendReq;
import com.person.system.pojo.entity.Friend;
import com.person.system.pojo.vo.FriendVO;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 10:54
 */
public interface FriendService {

    /**
     * 查询登录人好友
     */
    List<FriendVO> listFriend(ListFriendReq req);

    /**
     * 查询好友数量
     */
    Integer countFriend(ListFriendReq req);

    /**
     * 查询是否已经添加为好友
     * 根据当前登录人Id和好友用户Id
     */
    Friend findFriendByFriendUserId(String myUserId, String friendUserId);

    /**
     * 添加好友
     */
    boolean insertFriend(Friend friend);

    /**
     * 删除好友
     */
    boolean deleteFriend(String myUserId, String friendId);

}
