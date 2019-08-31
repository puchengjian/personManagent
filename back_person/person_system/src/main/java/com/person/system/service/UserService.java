package com.person.system.service;

import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.vo.UserVO;
import com.person.system.network.bean.user.InsertUserReq;
import com.person.system.network.bean.user.ListUserReq;
import com.person.system.network.bean.user.UpdateUserReq;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 15:41
 */
public interface UserService {

    /**
     * 根据用户名查询用户
     */
    UserVO findUserByAccount(String account);

    /**
     * 查询全部用户
     */
    List<UserVO> listUser(ListUserReq req);

    /**
     * 查询用户数量
     */
    Integer countUser(ListUserReq req);

    /**
     * 根据手机号查询用户信息
     */
    User findUserByPhone(String phone);


    /**
     * 根据Id查询用户信息
     */
    UserVO findUserById(String id);

    /**
     * 新增用户
     */
    boolean insertUser(InsertUserReq userReq);

    /**
     * 修改用户信息
     */
    boolean updateUserById(UpdateUserReq userReq);

    /**
     * 修改用户头像
     */
    boolean updateUserPhoto(String photo, String userId);

    /**
     * 根据Id删除用户
     */
    boolean deleteUserById(String id);

}
