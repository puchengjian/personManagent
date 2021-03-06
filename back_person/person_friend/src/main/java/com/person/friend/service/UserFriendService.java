package com.person.friend.service;

import com.person.auth.pojo.entity.UserFriend;
import com.person.friend.networ.bean.friend.ListFriendReq;
import com.person.friend.pojo.vo.FriendVO;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 10:54
 */
public interface UserFriendService {

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
    UserFriend findFriendByFriendUserId(String myUserId, String friendUserId);

    /**
     * 添加好友
     */
    boolean insertFriend(UserFriend userFriend);


}
