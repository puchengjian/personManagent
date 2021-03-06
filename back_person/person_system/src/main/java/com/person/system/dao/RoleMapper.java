package com.person.system.dao;

import com.person.system.network.bean.role.InsertRoleReq;
import com.person.system.network.bean.role.ListRoleReq;
import com.person.system.network.bean.role.UpdateRoleReq;
import com.person.system.pojo.entity.Role;
import com.person.system.pojo.vo.RoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:05
 */
@Mapper
public interface RoleMapper {

    List<Role> listRole(ListRoleReq req);

    Integer countRole(ListRoleReq req);

    RoleVO findRoleById(@Param("id") String id);

    Role findRoleByUserId(@Param("userId") String userId);

    Integer insertRole(InsertRoleReq req);

    Integer updateRoleById(UpdateRoleReq req);

    Integer deleteRoleById(@Param("id") String id);


}
