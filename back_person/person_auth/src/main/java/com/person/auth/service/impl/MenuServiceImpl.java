package com.person.auth.service.impl;

import com.person.auth.dao.MenuMapper;
import com.person.auth.dao.RoleMapper;
import com.person.auth.dao.RoleMenuMapper;
import com.person.auth.pojo.entity.Menu;
import com.person.auth.pojo.entity.Role;
import com.person.auth.pojo.entity.RoleMenu;
import com.person.auth.service.MenuService;
import com.person.auth.shiro.ShiroService;
import com.person.utils.BuildTree;
import com.person.utils.Tree;
import com.person.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: pzy
 * @create: 2019/8/8 16:31
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private ShiroService shiroService;

    @Override
    public List<Tree<Menu>> listNavMenu(String id) {
        List<Tree<Menu>> trees = new ArrayList<>();
        List<Menu> menus = menuMapper.listNavMenu(id);
        for (Menu menu : menus) {
            Tree tree = new Tree();
            tree.setId(menu.getId());
            tree.setText(menu.getMenuName());
            tree.setParentId(menu.getParentId());
            tree.setPath(menu.getPath());
            tree.setIcon(menu.getIcon());
            tree.setComponent(menu.getComponent());
            Map<String, Object> map = new HashMap<>();
            map.put("requireAuth", menu.isRequireAuth());
            tree.setMeta(map);
            trees.add(tree);
        }

        return BuildTree.buildList(trees);
    }

    @Override
    public List<String> listMenuPerms(String id) {
        return menuMapper.listMenuPerms(id);
    }

    @Override
    @Transactional
    public boolean insertMenu(Menu menu) {
        Role role = roleMapper.findRoleByUserId(shiroService.getId());
        menu.setId(UUIDUtils.getUUID());
        if (menuMapper.insertMenu(menu) > 0) {
            if (role.isAdmin()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setId(UUIDUtils.getUUID());
                roleMenu.setMenuId(menu.getId());
                roleMenu.setRoleId(role.getId());
                roleMenuMapper.insertRoleMenu(roleMenu);
            }
            return true;
        }

        return false;
    }

}
