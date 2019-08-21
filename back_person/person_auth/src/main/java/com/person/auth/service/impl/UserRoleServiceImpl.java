package com.person.auth.service.impl;

import com.person.auth.dao.UserRoleMapper;
import com.person.auth.pojo.entity.UserRole;
import com.person.auth.service.UserRoleService;
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
        if (userRoleMapper.insertUserRole(userRole) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean updateUserRoleByUserId(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        if (userRoleMapper.updateUserRoleByUserId(userRole) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteUserRoleByUserId(String userId) {
        if (userRoleMapper.deleteUserRoleByUserId(userId) > 0) {
            return true;
        }

        return false;
    }
}
