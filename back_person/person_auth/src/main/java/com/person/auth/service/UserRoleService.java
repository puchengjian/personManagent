package com.person.auth.service;


/**
 * @author: pzy
 * @create: 2019/8/21 11:07
 */
public interface UserRoleService {

    /**
     * 新增用户角色
     */
    boolean insertUserRole(String userId, String roleId);

    /**
     * 根据用户Id修改
     */
    boolean updateUserRoleByUserId(String userId, String roleId);

    /**
     * 根据用户Id删除
     */
    boolean deleteUserRoleByUserId(String userId);


}
