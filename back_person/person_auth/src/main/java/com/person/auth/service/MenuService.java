package com.person.auth.service;

import com.person.auth.network.bean.menu.InsertMenuReq;
import com.person.auth.network.bean.menu.ListMenuReq;
import com.person.auth.network.bean.menu.UpdateMenuReq;
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
     * 查询全部菜单
     * 根据用户Id
     */
    List<Tree<Menu>> listMenu(ListMenuReq req);

    /**
     * 查询菜单数量
     * 根据用户Id
     */
    Integer countMenu(ListMenuReq req);


    /**
     * 查询权限
     * 根据用户Id
     */
    List<String> listMenuPerms(String id);

    /**
     * 根据id查询菜单详情
     */
    Menu findMenuById(String id);

    /**
     * 新增菜单
     */
    boolean insertMenu(InsertMenuReq req);

    /**
     * 根据id修改菜单信息
     */
    boolean updateMenuById(UpdateMenuReq req);

    /**
     * 根据id删除
     */
    boolean deleteMenuById(String id);

}
