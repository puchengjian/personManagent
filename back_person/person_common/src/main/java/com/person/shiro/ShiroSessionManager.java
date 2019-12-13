package com.person.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

import static org.apache.shiro.web.servlet.ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE;

/**
 * 自定义session获取方式
 * 无状态token验证
 * @author: pzy
 * @create: 2019/8/20 13:53
 */
@Slf4j
public class ShiroSessionManager extends DefaultWebSessionManager {

    private static final String HEADER_TOKEN_NAME = "token";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        if (servletRequest.getRequestURI().equals("/favicon.ico")) {
            return null;
        }
        String token = WebUtils.toHttp(request).getHeader(HEADER_TOKEN_NAME);

        if (servletRequest.getRequestURI().startsWith("/friend/ws") && StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
            log.warn("ws token：{}", token );
        }
        if (StringUtils.isEmpty(token)) {
            //如果没有携带id参数则按照父类的方式在cookie进行获取
            return super.getSessionId(request, response);
        } else {
            //如果请求头中有 token 则其值为sessionId
            request.setAttribute(REFERENCED_SESSION_ID_SOURCE,  "Stateless request");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        }
    }
}
