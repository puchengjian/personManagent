package com.person.system.service.impl;

import com.person.system.dao.UserMapper;
import com.person.system.network.bean.user.InsertUserReq;
import com.person.system.network.bean.user.ListUserReq;
import com.person.system.network.bean.user.UpdateUserReq;
import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.vo.UserVO;
import com.person.system.service.UserRoleService;
import com.person.system.service.UserService;
import com.person.utils.MD5Utils;
import com.person.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserVO findUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
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
    public User findUserByPhone(String phone) {
        return userMapper.findUserByPhone(phone);
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

        return userMapper.updateUserPhoto(photo, userId) > 0;
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
