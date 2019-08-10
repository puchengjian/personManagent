package com.person.auth.dao;

import com.person.auth.pojo.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: pzy
 * @create: 2019/8/8 17:30
 */
@Mapper
public interface RoleMenuMapper {

    Integer insertRoleMenu(RoleMenu roleMenu);

}
