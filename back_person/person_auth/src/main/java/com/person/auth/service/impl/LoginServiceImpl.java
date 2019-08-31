package com.person.auth.service.impl;

import com.person.auth.dao.LoginMapper;
import com.person.auth.pojo.vo.UserVO;
import com.person.auth.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/31 18:16
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public UserVO login(String account) {
        return null;
    }

    @Override
    public List<String> listMenuPerms(String id) {
        return loginMapper.listMenuPerms(id);
    }


}
