package com.person.auth.dao;

import com.person.auth.network.bean.user.InsertUserReq;
import com.person.auth.network.bean.user.ListUserReq;
import com.person.auth.network.bean.user.UpdateUserReq;
import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.vo.UserVO;
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

    List<UserVO> listUser(ListUserReq req);

    Integer countUser(ListUserReq req);

    UserVO findUserById(@Param("id") String id);

    Integer insertUser(InsertUserReq userReq);

    Integer updateUserById(UpdateUserReq userReq);

    Integer deleteUserById(@Param("id") String id);

}
