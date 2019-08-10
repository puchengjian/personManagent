package com.person.auth.service;

import com.person.auth.pojo.entity.Role;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:08
 */
public interface RoleService {

    /**
     * 查询全部角色
     */
    List<Role> listRole();

    /**
     * 根据Id查询角色
     */
    Role findRoleById(String id);

    /**
     * 根据用户Id查询角色
     */
    Role findRoleByUserId(String userId);

}
