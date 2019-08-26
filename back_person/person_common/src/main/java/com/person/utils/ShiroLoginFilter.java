package com.person.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.person.constant.HttpConst;
import com.person.json.SuccessOrFailure;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: pzy
 * @create: 2019/8/10 10:23
 */
@Slf4j
public class ShiroLoginFilter extends FormAuthenticationFilter {


    /**
     * 返回false 执行onAccessDenied
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
            log.error("方法:{}", httpServletRequest.getRequestURI());
            if (httpServletRequest.getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }

        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     *
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //这里是个坑，如果不设置的接受的访问源，那么前端都会报跨域错误，因为这里还没到corsConfig里面
        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(JSON.toJSONString(SuccessOrFailure.FAILURE(HttpConst.UNAUTHORIZED, "请先登录")));
        return false;
    }


}
