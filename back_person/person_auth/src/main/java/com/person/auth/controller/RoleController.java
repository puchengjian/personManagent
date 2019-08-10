package com.person.auth.controller;

import com.person.auth.pojo.entity.Role;
import com.person.auth.service.RoleService;
import com.person.json.SuccessOrFailure;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 17:10
 */
@RestController
@Api(description = "角色管理")
@RequestMapping("/api/person/v1/auth")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role")
    @ApiOperation(value = "查询全部角色", response = Role.class)
    SuccessOrFailure listRole() {
        try {
            List<Role> roles = roleService.listRole();

            return SuccessOrFailure.SUCCESS(roles);
        } catch (Exception e) {
            log.error("查询全部角色异常:", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "根据Id查询角色", response = Role.class)
    SuccessOrFailure findRoleById(@PathVariable String id) {
        try {
            Role role = roleService.findRoleById(id);

            return SuccessOrFailure.SUCCESS(role);
        } catch (Exception e) {
            log.error("根据Id查询角色异常:", e);
            return SuccessOrFailure.FAILURE;
        }
    }

}
