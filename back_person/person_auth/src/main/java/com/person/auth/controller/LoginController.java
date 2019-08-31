package com.person.auth.controller;

import com.person.auth.network.bean.LoginReq;
import com.person.constant.HttpConst;
import com.person.json.SuccessOrFailure;
import com.person.redis.RedisService;
import com.person.auth.shiro.ShiroService;
import com.person.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: pzy
 * @create: 2019/8/5 15:44
 */
@RestController
@Api(description = "登录管理")
@Slf4j
public class LoginController {

    @Autowired
    private ShiroService shiroService;

    @PostMapping("/login")
    @ApiOperation("请求登录")
    SuccessOrFailure login(@RequestBody LoginReq loginReq) {
        String md5PassWord = MD5Utils.encrypt(loginReq.getAccount(), loginReq.getPassword());

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginReq.getAccount(), md5PassWord);
            subject.login(token);
        } catch (Exception e) {
            return SuccessOrFailure.FAILURE(HttpConst.FORBIDDEN, e.getMessage());
        }

        return SuccessOrFailure.SUCCESS(shiroService.getUser(), shiroService.getSession().getId());
    }


    @GetMapping("/logout")
    @ApiOperation("退出")
    public void logout() {
        shiroService.logout();
    }



}
