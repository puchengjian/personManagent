package com.person.auth.service.impl;

import com.person.auth.dao.BaseMapper;
import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.entity.UserFriend;
import com.person.auth.pojo.vo.UserVO;
import com.person.auth.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/9/2 10:03
 */
@Service
@Slf4j
public class BaseServiceImpl implements BaseService {

    @Autowired
    private BaseMapper baseMapper;

    @Override
    public UserVO findUserByAccount(String account) {
        return baseMapper.findUserByAccount(account);
    }

    @Override
    public User findUserByPhone(String phone) {
        return baseMapper.findUserByPhone(phone);
    }

    @Override
    public UserVO findUserById(String id) {
        return baseMapper.findUserById(id);
    }

    @Override
    @Transactional
    public boolean deleteFriend(String myUserId, String friendUserId) {
        if (baseMapper.deleteFriend(myUserId, friendUserId) > 0) {
            baseMapper.deleteChatFriend(myUserId, friendUserId);
            baseMapper.deleteChatFriend(friendUserId, myUserId);
            return baseMapper.deleteFriend(friendUserId, myUserId) > 0;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteChatFriend(String myUserId, String friendUserId) {
        if (baseMapper.deleteChatFriend(myUserId, friendUserId) > 0) {
            return baseMapper.deleteChatFriend(friendUserId, myUserId) > 0;
        }

        return false;
    }

    @Override
    public List<UserFriend> listUserFriend(String myUserId) {
        return baseMapper.listUserFriend(myUserId);
    }

    @Override
    public List<String> listMenuPerms(String id) {
        return baseMapper.listMenuPerms(id);
    }
}
