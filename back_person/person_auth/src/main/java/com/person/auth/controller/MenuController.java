package com.person.auth.controller;

import com.person.auth.pojo.entity.Menu;
import com.person.auth.service.MenuService;
import com.person.auth.shiro.ShiroService;
import com.person.constant.HttpConst;
import com.person.json.SuccessOrFailure;
import com.person.utils.Tree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 16:40
 */
@RestController
@Api(description = "菜单管理")
@RequestMapping("/api/person/v1/auth")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private ShiroService shiroService;

    @GetMapping("/nav/menu")
    @ApiOperation(value = "查询导航菜单", response = Menu.class)
    SuccessOrFailure listNavMenu() {
        try {
            List<Tree<Menu>> menuList = menuService.listNavMenu(shiroService.getId());

            return SuccessOrFailure.SUCCESS(menuList);
        } catch (Exception e) {
            log.error("查询导航菜单异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PostMapping("/menu")
    @ApiOperation(value = "新增菜单")
    SuccessOrFailure insertMenu(@RequestBody Menu menu) {
        try {
            boolean flag = menuService.insertMenu(menu);
            if (flag) {
                return SuccessOrFailure.SUCCESS("新增成功~");
            } else {
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "新增失败~");
            }
        } catch (Exception e) {
            log.error("新增菜单异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }


}
