package com.person.auth.controller;

import com.person.auth.network.bean.InsertUserReq;
import com.person.auth.network.bean.UpdateUserReq;
import com.person.auth.pojo.entity.User;
import com.person.auth.service.UserService;
import com.person.constant.HttpConst;
import com.person.json.SuccessOrFailure;
import com.person.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/8 15:38
 */
@RestController
@Api(description = "用户管理")
@RequestMapping("/api/person/v1/auth")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @ApiOperation(value = "查询全部用户", response = User.class)
    SuccessOrFailure listUser() {
        try {
            List<User> users = userService.listUser();
            Integer count = userService.countUser();

            return SuccessOrFailure.SUCCESS(users, count);
        } catch (Exception e) {
            log.error("查询全部用户异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "根据Id查询用户信息", response = User.class)
    SuccessOrFailure findUserById(@PathVariable String id) {
        try {
            User user = userService.findUserById(id);

            return SuccessOrFailure.SUCCESS(user);
        } catch (Exception e) {
            log.error("根据Id查询用户信息异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PostMapping("/user")
    @ApiOperation(value = "新增用户")
    SuccessOrFailure insertUser(@RequestBody InsertUserReq userReq) {
        try {
            if (userService.login(userReq.getAccount()) != null) {
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "用户名已存在~");
            }

            boolean flag = userService.insertUser(userReq);
            if (flag) {
                return SuccessOrFailure.SUCCESS("新增成功~");
            } else {
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "新增失败~");
            }

        } catch (Exception e) {
            log.error("新增用户异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PutMapping("/user")
    @ApiOperation(value = "修改用户")
    SuccessOrFailure updateUserById(@RequestBody UpdateUserReq userReq) {
        try {
            boolean flag = userService.updateUserById(userReq);
            if (flag) {
                return SuccessOrFailure.SUCCESS("修改成功~");
            } else {
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "修改失败~");
            }

        } catch (Exception e) {
            log.error("修改用户信息异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @DeleteMapping("/user/{id}")
    @ApiOperation(value = "删除用户")
    SuccessOrFailure deleteUserById(@PathVariable String id) {
        try {
            boolean flag = userService.deleteUserById(id);
            if (flag) {
                return SuccessOrFailure.SUCCESS("删除成功~");
            } else {
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "删除失败~");
            }

        } catch (Exception e) {
            log.error("删除用户异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/user/export")
    @ApiOperation(value = "导出excel", response = User.class)
    void exportUser(HttpServletResponse response) {
        try {
            List<User> users = userService.listUser();

            ExcelUtils.exportExcel(users, "用户数据", "用户数据", response, User.class);
        } catch (Exception e) {
            log.error("导出excel异常:{}", e);
        }
    }

}
