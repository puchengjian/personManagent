package com.person.auth.dao;

import com.person.auth.network.bean.InsertUserReq;
import com.person.auth.network.bean.UpdateUserReq;
import com.person.auth.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 15:35
 */
@Mapper
public interface UserMapper {

    User login(@Param("account") String account);

    List<User> listUser();

    Integer countUser();

    User findUserById(@Param("id") String id);

    Integer insertUser(InsertUserReq userReq);

    Integer updateUserById(UpdateUserReq userReq);

    Integer deleteUserById(@Param("id") String id);

}
