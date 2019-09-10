package com.person.friend.controller;

import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.entity.UserFriend;
import com.person.auth.service.BaseService;
import com.person.auth.shiro.ShiroService;
import com.person.constant.HttpConst;
import com.person.friend.networ.bean.friend.ListFriendReq;
import com.person.friend.pojo.vo.FriendVO;
import com.person.friend.service.UserFriendService;
import com.person.friend.webSocket.FriendMessageService;
import com.person.json.SuccessOrFailure;
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
@RequestMapping("/api/person/v1/friend")
@Slf4j
public class UserFriendController {

    @Autowired
    private UserFriendService userFriendService;
    @Autowired
    private FriendMessageService friendMessageService;
    @Autowired
    private BaseService baseService;


    @GetMapping("/user/friend")
    @ApiOperation(value = "查询全部好友", response = FriendVO.class)
    SuccessOrFailure listFriend(@Valid ListFriendReq req) {
        try {
            req.setUserId(ShiroService.getId());
            List<FriendVO> friendVOS = userFriendService.listFriend(req);
            Integer count = userFriendService.countFriend(req);

            return SuccessOrFailure.SUCCESS(friendVOS, count);
        } catch (Exception e) {
            log.error("查询全部好友异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @GetMapping("/user/friend/{phone}")
    @ApiOperation(value = "根据手机号查找好友", response = User.class)
    SuccessOrFailure findUserByPhone(@PathVariable String phone) {
        try {
            User user = baseService.findUserByPhone(phone);
            if (user == null)
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "用户不存在~");

            return SuccessOrFailure.SUCCESS(user);
        } catch (Exception e) {
            log.error("根据手机号查找好友异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @PostMapping("/user/friend")
    @ApiOperation(value = "添加好友")
    SuccessOrFailure insertFriend(@RequestBody UserFriend userFriend) {
        try {
            String myUserId = ShiroService.getId();
            String friendUserId = userFriend.getFriendUserId();

            if (StringUtils.isBlank(friendUserId))
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "您还没有查询好友~~");
            if (friendUserId.equals(myUserId))
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "不可以添加自身为好友~");
            if (userFriendService.findFriendByFriendUserId(myUserId, friendUserId) != null)
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "当前用户已是您好友~");

            userFriend.setMyUserId(myUserId);
            boolean flag = userFriendService.insertFriend(userFriend);
            if (flag) {
                friendMessageService.sendListToUser(myUserId, friendUserId, "/queue/friend/chat/list");

                return SuccessOrFailure.SUCCESS("添加好友成功~");
            } else
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "添加好友失败~");

        } catch (Exception e) {
            log.error("添加好友异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

    @DeleteMapping("/user/friend/{friendUserId}")
    @ApiOperation(value = "删除好友")
    SuccessOrFailure deleteFriend(@PathVariable String friendUserId) {
        try {
            boolean flag = baseService.deleteFriend(ShiroService.getId(), friendUserId);
            if (flag) {
                friendMessageService.sendListToUser(ShiroService.getId(), friendUserId, "/queue/friend/chat/list");

                return SuccessOrFailure.SUCCESS("删除好友成功~");
            } else
                return SuccessOrFailure.FAILURE(HttpConst.BAD_REQUEST, "删除好友失败~");

        } catch (Exception e) {
            log.error("删除好友异常:{}", e);
            return SuccessOrFailure.FAILURE;
        }
    }

}
