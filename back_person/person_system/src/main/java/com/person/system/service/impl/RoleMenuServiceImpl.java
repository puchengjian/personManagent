package com.person.system.service.impl;

import com.person.system.dao.RoleMenuMapper;
import com.person.system.pojo.entity.RoleMenu;
import com.person.system.service.RoleMenuService;
import com.person.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/21 9:23
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public boolean insertRoleMenu(String roleId, String menuId) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setId(UUIDUtils.getUUID());
        roleMenu.setRoleId(roleId);
        roleMenu.setMenuId(menuId);

        return roleMenuMapper.insertRoleMenu(roleMenu) > 0;
    }

    @Override
    public boolean insertAll(String roleId, List<String> menuIdList) {
        List<RoleMenu> roleMenuList = new ArrayList<>();
        for (String menuId : menuIdList) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setId(UUIDUtils.getUUID());
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }

        return roleMenuMapper.insertAll(roleMenuList) > 0;
    }

    @Override
    public boolean deleteByRoleId(String roleId) {

        return roleMenuMapper.deleteByRoleId(roleId) > 0;
    }

    @Override
    public boolean deleteByMenuId(String menuId) {

        return roleMenuMapper.deleteByMenuId(menuId) > 0;
    }
}
