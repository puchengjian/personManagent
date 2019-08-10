package com.person.auth.dao;

import com.person.auth.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:05
 */
@Mapper
public interface RoleMapper {

    List<Role> listRole();

    Role findRoleById(@Param("id") String id);

    Role findRoleByUserId(@Param("userId") String userId);


}
