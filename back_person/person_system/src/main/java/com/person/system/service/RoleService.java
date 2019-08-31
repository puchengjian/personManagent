package com.person.system.service;

import com.person.system.network.bean.role.InsertRoleReq;
import com.person.system.network.bean.role.ListRoleReq;
import com.person.system.network.bean.role.UpdateRoleReq;
import com.person.system.pojo.entity.Role;
import com.person.system.pojo.vo.RoleVO;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:08
 */
public interface RoleService {

    /**
     * 查询全部角色
     */
    List<Role> listRole(ListRoleReq req);

    /**
     * 查询角色数量
     */
    Integer countRole(ListRoleReq req);

    /**
     * 根据Id查询角色
     */
    RoleVO findRoleById(String id);

    /**
     * 根据用户Id查询角色
     */
    Role findRoleByUserId(String userId);

    /**
     * 新增角色
     */
    boolean insertRole(InsertRoleReq req);

    /**
     * 修改角色信息
     */
    boolean updateRoleById(UpdateRoleReq req);

    /**
     * 根据Id删除角色
     */
    boolean deleteRoleById(String id);


}
