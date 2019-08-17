package com.person.auth.service.impl;

import com.person.auth.dao.UserMapper;
import com.person.auth.network.bean.InsertUserReq;
import com.person.auth.network.bean.UpdateUserReq;
import com.person.auth.pojo.entity.User;
import com.person.auth.service.UserService;
import com.person.utils.MD5Utils;
import com.person.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 15:42
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String account) {
        return userMapper.login(account);
    }

    @Override
    public List<User> listUser() {
        return userMapper.listUser();
    }

    @Override
    public Integer countUser() {
        return userMapper.countUser();
    }

    @Override
    public User findUserById(String id) {
        return userMapper.findUserById(id);
    }

    @Override
    public boolean insertUser(InsertUserReq userReq) {
        userReq.setId(UUIDUtils.getUUID());
        userReq.setPassword(MD5Utils.encrypt(userReq.getAccount(), userReq.getPassword()));
        if (userMapper.insertUser(userReq) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean updateUserById(UpdateUserReq userReq) {
        if (userMapper.updateUserById(userReq) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteUserById(String id) {
        if (userMapper.deleteUserById(id) > 0) {
            return true;
        }

        return false;
    }

}
