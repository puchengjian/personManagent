package com.person.system.service.impl;

import com.person.system.dao.MenuMapper;
import com.person.system.dao.RoleMapper;
import com.person.system.network.bean.menu.InsertMenuReq;
import com.person.system.network.bean.menu.ListMenuReq;
import com.person.system.network.bean.menu.UpdateMenuReq;
import com.person.system.pojo.entity.Menu;
import com.person.system.pojo.entity.Role;
import com.person.system.service.MenuService;
import com.person.system.service.RoleMenuService;
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
    private RoleMenuService roleMenuService;
    @Autowired
    private ShiroService shiroService;

    @Override
    public List<Tree<Menu>> listNavMenu(String id) {
        List<Menu> menus = menuMapper.listNavMenu(id);

        return BuildTree.buildList(listTreeMenu(menus));
    }

    @Override
    public List<Tree<Menu>> listMenu(ListMenuReq req) {
        List<Menu> menus = menuMapper.listMenu(req);
        List<Tree<Menu>> trees = BuildTree.buildList(listTreeMenu(menus));
        if (menus.size() > 0 && trees.size() <= 0)
            return listTreeMenu(menus);

        return trees;
    }

    @Override
    public Integer countMenu(ListMenuReq req) {
        return menuMapper.countMenu(req);
    }

    @Override
    public Menu findMenuById(String id) {
        return menuMapper.findMenuById(id);
    }

    @Override
    @Transactional
    public boolean insertMenu(InsertMenuReq req) {
        Role role = roleMapper.findRoleByUserId(shiroService.getId());
        req.setId(UUIDUtils.getUUID());
        if (menuMapper.insertMenu(req) > 0) {
            if (role.isAdmin())
                roleMenuService.insertRoleMenu(role.getId(), req.getId());

            return true;
        }

        return false;
    }

    @Override
    public boolean updateMenuById(UpdateMenuReq req) {

        return menuMapper.updateMenuById(req) > 0;
    }

    @Override
    @Transactional
    public boolean deleteMenuById(String id) {
        if (menuMapper.deleteMenuById(id) > 0) {
            roleMenuService.deleteByMenuId(id);
            return true;
        }

        return false;
    }

    /**
     * 查询的菜单数据转换成Tree数据
     */
    private List<Tree<Menu>> listTreeMenu(List<Menu> menus) {
        List<Tree<Menu>> trees = new ArrayList<>();

        for (Menu menu : menus) {
            Tree<Menu> tree = new Tree<Menu>();
            tree.setId(menu.getId());
            tree.setText(menu.getMenuName());
            tree.setParentId(menu.getParentId());
            tree.setPath(menu.getPath());
            tree.setIcon(menu.getIcon());
            tree.setComponent(menu.getComponent());
            tree.setFolder(menu.getFolder());
            Map<String, Object> map = new HashMap<>();
            map.put("requireAuth", menu.isRequireAuth());
            tree.setMeta(map);
            tree.setType(menu.getType() == 1 ? "菜单" : "按钮");
            tree.setPerms(menu.getPerms());
            tree.setEnabled(menu.isEnabled());
            trees.add(tree);
        }

        return trees;
    }

}
