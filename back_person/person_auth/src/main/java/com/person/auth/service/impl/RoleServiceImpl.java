package com.person.auth.service.impl;

import com.person.auth.dao.RoleMapper;
import com.person.auth.pojo.entity.Role;
import com.person.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:09
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> listRole() {
        return roleMapper.listRole();
    }

    @Override
    public Role findRoleById(String id) {
        return roleMapper.findRoleById(id);
    }

    @Override
    public Role findRoleByUserId(String userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
