package com.cn.miao.security.handler;

import com.cn.miao.common.model.Result;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cn.miao.common.constant.CommonConstant.LoginError;

/**
 * @title: TotoroAuthenticationFailHandler
 * @description: 登录失败处理器
 * @author: dengmiao
 * @create: 2019-07-15 10:44
 **/
@Component
public class TotoroAuthenticationFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (e instanceof UsernameNotFoundException) {
            Result.response(httpServletResponse, Result.error(LoginError.USER_NOT_EXISTS));
        } else if (e instanceof BadCredentialsException) {
            Result.response(httpServletResponse, Result.error(LoginError.USERNAME_PASSWORD_ERROR));
        } else if (e instanceof LockedException) {
            Result.response(httpServletResponse, Result.error(LoginError.USER_LOCKED));
        }
        Result.response(httpServletResponse, Result.error(LoginError.SYSTEM_ERROR));
    }
}
