package com.person.system.dao;

import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.vo.UserVO;
import com.person.system.network.bean.user.InsertUserReq;
import com.person.system.network.bean.user.ListUserReq;
import com.person.system.network.bean.user.UpdateUserReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 15:35
 */
@Mapper
public interface UserMapper {

    List<UserVO> listUser(ListUserReq req);

    Integer countUser(ListUserReq req);

    Integer insertUser(InsertUserReq userReq);

    Integer updateUserById(UpdateUserReq userReq);

    Integer updateUserPhoto(@Param("photo") String photo, @Param("userId") String userId);

    Integer deleteUserById(@Param("id") String id);

}
