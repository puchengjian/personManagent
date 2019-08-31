package com.person.system.service.impl;

import com.person.system.dao.UserRoleMapper;
import com.person.system.pojo.entity.UserRole;
import com.person.system.service.UserRoleService;
import com.person.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: pzy
 * @create: 2019/8/21 11:08
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean insertUserRole(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setId(UUIDUtils.getUUID());
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);

        return userRoleMapper.insertUserRole(userRole) > 0;
    }

    @Override
    public boolean updateUserRoleByUserId(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);

        return userRoleMapper.updateUserRoleByUserId(userRole) > 0;
    }

    @Override
    public boolean deleteUserRoleByUserId(String userId) {

        return userRoleMapper.deleteUserRoleByUserId(userId) > 0;
    }
}
