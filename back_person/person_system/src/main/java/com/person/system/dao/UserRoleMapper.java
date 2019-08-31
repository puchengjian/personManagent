package com.person.system.dao;

import com.person.system.pojo.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: pzy
 * @create: 2019/8/21 11:05
 */
@Mapper
public interface UserRoleMapper {

    Integer insertUserRole(UserRole userRole);

    Integer updateUserRoleByUserId(UserRole userRole);

    Integer deleteUserRoleByUserId(String userId);

}
