package com.person.auth.dao;

import com.person.auth.network.bean.menu.InsertMenuReq;
import com.person.auth.network.bean.menu.ListMenuReq;
import com.person.auth.network.bean.menu.UpdateMenuReq;
import com.person.auth.pojo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 16:18
 */
@Mapper
public interface MenuMapper {

    List<Menu> listNavMenu(@Param("id") String id);

    List<Menu> listMenu(ListMenuReq req);

    Integer countMenu(ListMenuReq req);

    List<String> listMenuPerms(@Param("id") String id);

    Menu findMenuById(@Param("id") String id);

    Integer insertMenu(InsertMenuReq req);

    Integer updateMenuById(UpdateMenuReq req);

    Integer deleteMenuById(@Param("id") String id);
}
