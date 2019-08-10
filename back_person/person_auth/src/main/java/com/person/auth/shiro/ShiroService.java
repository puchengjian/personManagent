package com.person.auth.shiro;

import com.person.auth.pojo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * @author: pzy
 * @create: 2019/8/5 16:46
 */
@Component
public class ShiroService {

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public void logout() {
        getSubject().logout();
    }

    public Session getSession() {
        return getSubject().getSession();
    }

    public User getUser() {
        return (User) getSubject().getPrincipal();
    }

    public String getAccount() {
        return getUser().getAccount();
    }

    public String getId() {
        return getUser().getId();
    }

}
