package com.person.auth.dao;

import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.entity.UserFriend;
import com.person.auth.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/9/2 10:03
 */
@Mapper
public interface BaseMapper {

    /**
     * User 用户
     */

    UserVO findUserByAccount(@Param("account") String account);

    User findUserByPhone(@Param("phone") String phone);

    UserVO findUserById(@Param("id") String id);

    /**
     * 用户好友
     */

    Integer deleteFriend(@Param("myUserId") String myUserId, @Param("friendUserId") String friendUserId);

    Integer deleteChatFriend(@Param("myUserId") String myUserId,@Param("friendUserId") String friendUserId);

    List<UserFriend> listUserFriend(@Param("myUserId") String myUserId);

    /**
     * Menu 菜单
     */

    List<String> listMenuPerms(@Param("id") String id);


}
