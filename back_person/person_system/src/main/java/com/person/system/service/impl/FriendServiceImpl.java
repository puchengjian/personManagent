package com.person.system.service.impl;

import com.person.system.dao.FriendMapper;
import com.person.system.network.bean.friend.ListFriendReq;
import com.person.system.pojo.entity.Friend;
import com.person.system.pojo.vo.FriendVO;
import com.person.system.service.FriendService;
import com.person.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 10:56
 */
@Service
@Slf4j
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;


    @Override
    public List<FriendVO> listFriend(ListFriendReq req) {
        return friendMapper.listFriend(req);
    }

    @Override
    public Integer countFriend(ListFriendReq req) {
        return friendMapper.countFriend(req);
    }

    @Override
    public Friend findFriendByFriendUserId(String myUserId, String friendUserId) {
        return friendMapper.findFriendByFriendUserId(myUserId, friendUserId);
    }

    @Override
    @Transactional
    public boolean insertFriend(Friend friend) {
        friend.setId(UUIDUtils.getUUID());
        if (friendMapper.insertFriend(friend) > 0) {
            friend.setId(UUIDUtils.getUUID());
            String friendUserId = friend.getFriendUserId();
            friend.setFriendUserId(friend.getMyUserId());
            friend.setMyUserId(friendUserId);
            return friendMapper.insertFriend(friend) > 0;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteFriend(String myUserId, String friendId) {
        if (friendMapper.deleteFriend(myUserId, friendId) > 0) {

            return friendMapper.deleteFriend(friendId, myUserId) > 0;
        }

        return false;
    }

}
