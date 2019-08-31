package com.person.auth.dao;

import com.person.auth.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 18:13
 */
@Mapper
public interface LoginMapper {

    UserVO login(@Param("account") String account);

    List<String> listMenuPerms(@Param("id") String id);


}
