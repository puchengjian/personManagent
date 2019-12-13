package com.person.system.service.impl;

import com.person.system.dao.RoleMapper;
import com.person.system.network.bean.role.InsertRoleReq;
import com.person.system.network.bean.role.ListRoleReq;
import com.person.system.network.bean.role.UpdateRoleReq;
import com.person.system.pojo.entity.Role;
import com.person.system.pojo.vo.RoleVO;
import com.person.system.service.RoleMenuService;
import com.person.system.service.RoleService;
import com.person.constant.RedisConst;
import com.person.redis.RedisService;
import com.person.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:09
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RedisService redisService;


    @Override
    public List<Role> listRole(ListRoleReq req) {
        return roleMapper.listRole(req);
    }

    @Override
    public Integer countRole(ListRoleReq req) {
        return roleMapper.countRole(req);
    }

    @Override
    public RoleVO findRoleById(String id) {
        return roleMapper.findRoleById(id);
    }

    @Override
    public Role findRoleByUserId(String userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    @Override
    @Transactional
    public boolean insertRole(InsertRoleReq req) {
        req.setId(UUIDUtils.getUUID());
        if (roleMapper.insertRole(req) > 0) {
            if (req.getMenuIdList() != null && req.getMenuIdList().size() > 0)
                roleMenuService.insertAll(req.getId(), req.getMenuIdList());
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean updateRoleById(UpdateRoleReq req) {
        if (roleMapper.updateRoleById(req) > 0) {
            redisService.remove(RedisConst.SHIRO_CACHE_KEYPREFIX + RedisConst.SHIRO_CACHE_NAME + req.getId());
            roleMenuService.deleteByRoleId(req.getId());
            roleMenuService.insertAll(req.getId(), req.getMenuIdList());
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteRoleById(String id) {
        if (roleMapper.deleteRoleById(id) > 0) {
            redisService.remove(RedisConst.SHIRO_CACHE_KEYPREFIX + RedisConst.SHIRO_CACHE_NAME + id);
            roleMenuService.deleteByRoleId(id);
            return true;
        }

        return false;
    }
}
