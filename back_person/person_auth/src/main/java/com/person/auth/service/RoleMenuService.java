package com.person.auth.service;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/21 9:22
 */
public interface RoleMenuService {

    /**
     * 新增单条数据
     */
    boolean insertRoleMenu(String roleId, String menuId);

    /**
     * 新增多条数据
     */
    boolean insertAll(String roleId, List<String> menuIdList);

    /**
     * 根据角色Id删除
     */
    boolean deleteByRoleId(String roleId);

    /**
     * 根据菜单Id删除
     */
    boolean deleteByMenuId(String menuId);



}
