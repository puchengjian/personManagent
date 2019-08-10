package com.person.auth.service;

import com.person.auth.pojo.entity.Menu;
import com.person.utils.Tree;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 16:31
 */
public interface MenuService {

    /**
     * 查询导航菜单
     * 根据用户Id
     */
    List<Tree<Menu>> listNavMenu(String id);

    /**
     * 查询权限
     * 根据用户Id
     */
    List<String> listMenuPerms(String id);

    /**
     * 新增菜单
     */
    boolean insertMenu(Menu menu);


}
