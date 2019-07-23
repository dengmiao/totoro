package com.cn.miao.security.filter;

import com.cn.miao.common.model.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title: HttpAuthenticationEntryPoint
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 15:06
 **/
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        Result.response(httpServletResponse, Result.unauthorized(e.getMessage()));
    }
}
