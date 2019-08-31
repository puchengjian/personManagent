package com.person.auth.service;

import com.person.auth.pojo.vo.UserVO;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 18:16
 */
public interface LoginService {

    /**
     * 登录
     */
    UserVO login(String account);

    /**
     * 查询权限
     * 根据用户Id
     */
    List<String> listMenuPerms(String id);

}
