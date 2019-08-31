package com.person.auth.shiro;

import com.person.auth.pojo.entity.User;
import com.person.auth.pojo.vo.UserVO;
import com.person.auth.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * @author: pzy
 * @create: 2019/8/5 16:26
 */
@Slf4j
public class AuthRealm extends AuthorizingRealm {

    @Resource
    private LoginService loginService;
    @Autowired
    private ShiroService shiroService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.warn("进入角色授权！");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = shiroService.getUser();
        List<String> perms = loginService.listMenuPerms(user.getId());
        simpleAuthorizationInfo.setStringPermissions(new HashSet<>(perms));

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取账号和密码
        String account = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        UserVO user = loginService.login(account);
        if (user == null) {
            throw  new UnknownAccountException("账号不正确~");
        }

        if (!user.isEnabled()) {
            throw  new AuthenticationException("账号已被锁定~");
        }

        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("密码不正确~");
        }

        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
