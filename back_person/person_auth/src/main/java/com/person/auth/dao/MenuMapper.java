package com.person.auth.dao;

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

    List<String> listMenuPerms(@Param("id") String id);

    Integer insertMenu(Menu menu);

}
