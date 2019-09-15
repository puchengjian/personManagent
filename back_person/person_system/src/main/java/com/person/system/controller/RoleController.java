package com.person.system.controller;

import com.person.system.network.bean.role.InsertRoleReq;
import com.person.system.network.bean.role.ListRoleReq;
import com.person.system.network.bean.role.UpdateRoleReq;
import com.person.system.pojo.entity.Role;
import com.person.constant.HttpConst;
import com.person.json.SuccessOrFailure;
import com.person.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:10
 */
@RestController
@Api(description = "角色管理")
@RequestMapping("/api/person/v1/system")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role")
    @ApiOperation(value = "查询全部角色", response = Role.class)
    @RequiresPermissions("role:sel")
    SuccessOrFailure listRole(@Valid ListRoleReq req) {
        try {
            List<Role> roles = roleService.listRole(req);
            Integer count = roleService.countRole(req);

            return SuccessOrFailure.SUCCESS(roles, count);
        } catch (Exception e) {
            log.error("查询全部角色异常:", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "根据Id查询角色", response = Role.class)
    @RequiresPermissions("role:get")
    SuccessOrFailure findRoleById(@PathVariable String id) {
        try {
            Role role = roleService.findRoleById(id);

            return SuccessOrFailure.SUCCESS(role);
        } catch (Exception e) {
            log.error("根据Id查询角色异常:", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PostMapping("/role")
    @ApiOperation(value = "新增角色")
    @RequiresPermissions("role:add")
    SuccessOrFailure insertRole(@RequestBody InsertRoleReq req) {
        try {
            boolean flag = roleService.insertRole(req);
            if (flag)
                return SuccessOrFailure.SUCCESS("新增成功~");
            else
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "新增失败~");

        } catch (Exception e) {
            log.error("新增角色异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PutMapping("/role")
    @ApiOperation(value = "修改角色")
    @RequiresPermissions("role:upd")
    SuccessOrFailure updateRoleById(@RequestBody UpdateRoleReq req) {
        try {
            boolean flag = roleService.updateRoleById(req);
            if (flag)
                return SuccessOrFailure.SUCCESS("修改成功~");
            else
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "修改失败~");

        } catch (Exception e) {
            log.error("修改角色信息异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "删除角色")
    @RequiresPermissions("role:del")
    SuccessOrFailure deleteRoleById(@PathVariable String id) {
        try {
            boolean flag = roleService.deleteRoleById(id);
            if (flag)
                return SuccessOrFailure.SUCCESS("删除成功~");
            else
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "删除失败~");

        } catch (Exception e) {
            log.error("删除角色异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

}
