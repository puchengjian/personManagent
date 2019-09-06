package com.person.chat.service.impl;

import com.person.chat.dao.ChatFriendMapper;
import com.person.chat.dao.UserFriendMapper;
import com.person.chat.network.bean.friend.ListFriendReq;
import com.person.auth.pojo.entity.UserFriend;
import com.person.chat.pojo.vo.FriendVO;
import com.person.chat.service.UserFriendService;
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
public class UserFriendServiceImpl implements UserFriendService {

    @Autowired
    private UserFriendMapper userFriendMapper;
    @Autowired
    private ChatFriendMapper chatFriendMapper;


    @Override
    public List<FriendVO> listFriend(ListFriendReq req) {
        return userFriendMapper.listFriend(req);
    }

    @Override
    public Integer countFriend(ListFriendReq req) {
        return userFriendMapper.countFriend(req);
    }

    @Override
    public UserFriend findFriendByFriendUserId(String myUserId, String friendUserId) {
        return userFriendMapper.findFriendByFriendUserId(myUserId, friendUserId);
    }

    @Override
    @Transactional
    public boolean insertFriend(UserFriend userFriend) {
        userFriend.setId(UUIDUtils.getUUID());
        if (userFriendMapper.insertFriend(userFriend) > 0) {
            userFriend.setId(UUIDUtils.getUUID());
            String friendUserId = userFriend.getFriendUserId();
            userFriend.setFriendUserId(userFriend.getMyUserId());
            userFriend.setMyUserId(friendUserId);
            return userFriendMapper.insertFriend(userFriend) > 0;
        }

        return false;
    }



}
