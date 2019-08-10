package com.person.auth.service;

import com.person.auth.network.bean.InsertUserReq;
import com.person.auth.network.bean.UpdateUserReq;
import com.person.auth.pojo.entity.User;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 15:41
 */
public interface UserService {

    /**
     * 登录
     */
    User login(String account);

    /**
     * 查询全部用户
     */
    List<User> listUser();

    /**
     * 根据Id查询用户信息
     */
    User findUserById(String id);

    /**
     * 新增用户
     */
    boolean insertUser(InsertUserReq userReq);

    /**
     * 修改用户信息
     */
    boolean updateUserById(UpdateUserReq userReq);

    /**
     * 根据Id删除用户
     */
    boolean deleteUserById(String id);

}
