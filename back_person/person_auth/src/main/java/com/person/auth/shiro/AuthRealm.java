package com.person.auth.shiro;

import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.vo.UserVO;
import com.person.auth.service.BaseService;
import com.person.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 16:26
 */
@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private BaseService baseService;
    @Autowired
    private RedisService redisService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.warn("进入角色授权！");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = ShiroService.getUser();
        List<String> perms = baseService.listMenuPerms(user.getId());
        simpleAuthorizationInfo.setStringPermissions(new HashSet<>(perms));

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取账号和密码
        String account = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        UserVO user = baseService.findUserByAccount(account);
        if (user == null) {
            throw  new UnknownAccountException("账号不正确~");
        }

        if (!user.isEnabled()) {
            throw  new AuthenticationException("账号已被锁定~");
        }

        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("密码不正确~");
        }
        redisService.set("user", user);
        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
