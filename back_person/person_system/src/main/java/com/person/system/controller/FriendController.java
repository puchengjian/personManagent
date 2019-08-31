package com.person.system.controller;

import com.person.system.network.bean.friend.ListFriendReq;
import com.person.system.pojo.entity.Friend;
import com.person.auth.pojo.entity.User;
import com.person.system.pojo.vo.FriendVO;
import com.person.constant.HttpConst;
import com.person.json.SuccessOrFailure;
import com.person.system.service.FriendService;
import com.person.system.service.UserService;
import com.person.auth.shiro.ShiroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 11:10
 */
@RestController
@Api(description = "好友管理")
@RequestMapping("/api/person/v1/system")
@Slf4j
public class FriendController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShiroService shiroService;


    @GetMapping("/friend")
    @ApiOperation(value = "查询全部好友", response = FriendVO.class)
    SuccessOrFailure listFriend(@Valid ListFriendReq req) {
        try {
            req.setUserId(shiroService.getId());
            List<FriendVO> friendVOS = friendService.listFriend(req);
            Integer count = friendService.countFriend(req);

            return SuccessOrFailure.SUCCESS(friendVOS, count);
        } catch (Exception e) {
            log.error("查询全部好友异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/friend/{phone}")
    @ApiOperation(value = "根据手机号查找好友", response = User.class)
    SuccessOrFailure findUserByPhone(@PathVariable String phone) {
        try {
            User user = userService.findUserByPhone(phone);
            if (user == null)
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "用户不存在~");

            return SuccessOrFailure.SUCCESS(user);
        } catch (Exception e) {
            log.error("根据手机号查找好友异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PostMapping("/friend")
    @ApiOperation(value = "添加好友")
    SuccessOrFailure insertFriend(@RequestBody Friend friend) {
        try {
            String myUserId = shiroService.getId();
            String friendUserId = friend.getFriendUserId();

            if (StringUtils.isBlank(friendUserId))
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "您还没有查询好友~~");
            if (friendUserId.equals(myUserId))
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "不可以添加自身为好友~");
            if (friendService.findFriendByFriendUserId(myUserId, friendUserId) != null)
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "当前用户已是您好友~");

            friend.setMyUserId(myUserId);
            boolean flag = friendService.insertFriend(friend);
            if (flag)
                return SuccessOrFailure.SUCCESS("添加好友成功~");
            else
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "添加好友失败~");

        } catch (Exception e) {
            log.error("添加好友异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @DeleteMapping("/friend/{friendUserId}")
    @ApiOperation(value = "删除好友")
    SuccessOrFailure deleteFriend(@PathVariable String friendUserId) {
        try {
            boolean flag = friendService.deleteFriend(shiroService.getId(), friendUserId);
            if (flag)
                return SuccessOrFailure.SUCCESS("删除好友成功~");
            else
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "删除好友失败~");

        } catch (Exception e) {
            log.error("删除好友异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

}
