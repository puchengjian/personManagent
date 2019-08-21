package com.person.auth.dao;

import com.person.auth.pojo.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:30
 */
@Mapper
public interface RoleMenuMapper {

    Integer insertRoleMenu(RoleMenu roleMenu);

    Integer insertAll(@Param("roleMenuList") List<RoleMenu> roleMenuList);

    Integer deleteByRoleId(@Param("roleId") String roleId);

    Integer deleteByMenuId(@Param("menuId") String menuId);


}
