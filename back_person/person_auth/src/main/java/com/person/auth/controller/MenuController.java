package com.person.auth.controller;

import com.person.auth.network.bean.menu.InsertMenuReq;
import com.person.auth.network.bean.menu.ListMenuReq;
import com.person.auth.network.bean.menu.UpdateMenuReq;
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

import javax.validation.Valid;
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
    @ApiOperation(value = "查询导航菜单", response = Tree.class)
    SuccessOrFailure listNavMenu() {
        try {
            List<Tree<Menu>> menuList = menuService.listNavMenu(shiroService.getId());

            return SuccessOrFailure.SUCCESS(menuList);
        } catch (Exception e) {
            log.error("查询导航菜单异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/menu")
    @ApiOperation(value = "查询全部菜单", response = Tree.class)
    SuccessOrFailure listMenu(@Valid ListMenuReq req) {
        try {
            req.setUserId(shiroService.getId());
            List<Tree<Menu>> menuList = menuService.listMenu(req);
            Integer count = menuService.countMenu(req);

            return SuccessOrFailure.SUCCESS(menuList, count);
        } catch (Exception e) {
            log.error("查询全部菜单异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/menu/{id}")
    @ApiOperation(value = "根据ID查询菜单详情", response = Menu.class)
    SuccessOrFailure findMenuById(@PathVariable String id) {
        try {
            Menu menu = menuService.findMenuById(id);

            return SuccessOrFailure.SUCCESS(menu);
        } catch (Exception e) {
            log.error("根据ID查询菜单详情异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PostMapping("/menu")
    @ApiOperation(value = "新增菜单")
    SuccessOrFailure insertMenu(@RequestBody InsertMenuReq req) {
        try {
            boolean flag = menuService.insertMenu(req);
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

    @PutMapping("/menu")
    @ApiOperation(value = "修改菜单")
    SuccessOrFailure updateUserById(@RequestBody UpdateMenuReq req) {
        try {
            boolean flag = menuService.updateMenuById(req);
            if (flag) {
                return SuccessOrFailure.SUCCESS("修改成功~");
            } else {
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "修改失败~");
            }

        } catch (Exception e) {
            log.error("修改菜单信息异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @DeleteMapping("/menu/{id}")
    @ApiOperation(value = "删除菜单")
    SuccessOrFailure deleteUserById(@PathVariable String id) {
        try {
            boolean flag = menuService.deleteMenuById(id);
            if (flag) {
                return SuccessOrFailure.SUCCESS("删除成功~");
            } else {
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "删除失败~");
            }

        } catch (Exception e) {
            log.error("删除菜单异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }


}
