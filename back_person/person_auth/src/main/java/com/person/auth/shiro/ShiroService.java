package com.person.auth.shiro;

import com.person.auth.pojo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @author: pzy
 * @create: 2019/8/5 16:46
 */
public class ShiroService {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static void logout() {
        getSubject().logout();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static User getUser() {
        return (User) getSubject().getPrincipal();
    }

    public static String getAccount() {
        return getUser().getAccount();
    }

    public static String getId() {
        return getUser().getId();
    }

}
