package com.person.auth.service;

import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.entity.UserFriend;
import com.person.auth.pojo.vo.UserVO;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/9/2 10:03
 */
public interface BaseService {

    /* User 用户 */

    /**
     * 根据用户名查询用户
     */
    UserVO findUserByAccount(String account);

    /**
     * 根据手机号查询用户
     */
    User findUserByPhone(String phone);

    /**
     * 根据Id查询用户信息
     */
    UserVO findUserById(String id);

    /* 用户好友 */

    /**
     * 删除好友
     */
    boolean deleteFriend(String myUserId, String friendUserId);

    /**
     * 删除好友聊天记录
     */
    boolean deleteChatFriend(String myUserId, String friendUserId);

    /**
     * 查询好友列表
     */
    List<UserFriend> listUserFriend(String myUserId);

    /* Menu 菜单 */

    /**
     *  查询权限
     */
    List<String> listMenuPerms(String id);

}
