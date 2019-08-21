package com.person.auth.service.impl;

import com.person.auth.dao.RoleMenuMapper;
import com.person.auth.pojo.entity.RoleMenu;
import com.person.auth.service.RoleMenuService;
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
        if (roleMenuMapper.insertRoleMenu(roleMenu) > 0) {
            return true;
        }

        return false;
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
        if (roleMenuMapper.insertAll(roleMenuList) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteByRoleId(String roleId) {
        if (roleMenuMapper.deleteByRoleId(roleId) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteByMenuId(String menuId) {
        if (roleMenuMapper.deleteByMenuId(menuId) > 0) {
            return true;
        }

        return false;
    }
}
