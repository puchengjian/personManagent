package com.person.utils;

import com.person.constant.HttpConst;
import com.person.json.SuccessOrFailure;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;

@Slf4j
@ControllerAdvice
@Component
public class MyHandlerException {

    @ExceptionHandler({AuthorizationException.class, UnauthorizedException.class})
    @ResponseBody
    public SuccessOrFailure authorizationException(Exception e) {
        return SuccessOrFailure.FAILURE(HttpConst.UNAUTHORIZED, "您的权限不足，无法操作~");
    }


}
