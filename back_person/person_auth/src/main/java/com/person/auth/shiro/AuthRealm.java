package com.person.auth.shiro;

import com.person.auth.pojo.entity.User;
import com.person.auth.service.MenuService;
import com.person.auth.service.UserService;
import org.apache.shiro.SecurityUtils;
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
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ShiroService shiroService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = shiroService.getUser();
        List<String> perms = menuService.listMenuPerms(user.getId());
        simpleAuthorizationInfo.setStringPermissions(new HashSet<>(perms));

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取账号和密码
        String account = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        User user = userService.login(account);
        if (user == null) {
            throw  new UnknownAccountException("账号不正确~");
        }

        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("密码不正确~");
        }

        return new SimpleAuthenticationInfo(user, password, getName());
    }
}
