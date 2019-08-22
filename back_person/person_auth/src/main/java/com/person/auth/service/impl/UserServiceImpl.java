package com.person.auth.service.impl;

import com.person.auth.dao.UserMapper;
import com.person.auth.network.bean.user.InsertUserReq;
import com.person.auth.network.bean.user.ListUserReq;
import com.person.auth.network.bean.user.UpdateUserReq;
import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.vo.UserVO;
import com.person.auth.service.UserRoleService;
import com.person.auth.service.UserService;
import com.person.utils.MD5Utils;
import com.person.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 15:42
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleService userRoleService;


    @Override
    public User login(String account) {
        return userMapper.login(account);
    }

    @Override
    public List<UserVO> listUser(ListUserReq req) {
        return userMapper.listUser(req);
    }

    @Override
    public Integer countUser(ListUserReq req) {
        return userMapper.countUser(req);
    }

    @Override
    public UserVO findUserById(String id) {
        return userMapper.findUserById(id);
    }

    @Override
    @Transactional
    public boolean insertUser(InsertUserReq userReq) {
        userReq.setId(UUIDUtils.getUUID());
        userReq.setPassword(MD5Utils.encrypt(userReq.getAccount(), userReq.getPassword()));
        if (userMapper.insertUser(userReq) > 0) {
            userRoleService.insertUserRole(userReq.getId(), userReq.getRoleId());
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean updateUserById(UpdateUserReq userReq) {
        if (userMapper.updateUserById(userReq) > 0) {
            boolean flag = userRoleService.updateUserRoleByUserId(userReq.getId(), userReq.getRoleId());
            if (!flag) {
                userRoleService.insertUserRole(userReq.getId(), userReq.getRoleId());
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean updateUserPhoto(String photo, String userId) {
        if (userMapper.updateUserPhoto(photo, userId) > 0) {
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteUserById(String id) {
        if (userMapper.deleteUserById(id) > 0) {
            userRoleService.deleteUserRoleByUserId(id);
            return true;
        }

        return false;
    }

}
