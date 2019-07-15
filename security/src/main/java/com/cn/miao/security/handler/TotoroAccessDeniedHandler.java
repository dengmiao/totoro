package com.cn.miao.security.handler;

import com.cn.miao.common.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title: TotoroAccessDeniedHandler
 * @description: 权限不足处理器
 * @author: dengmiao
 * @create: 2019-07-15 11:33
 **/
@Component
public class TotoroAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Result.response(httpServletResponse, Result.wrap(Result.forbidden("抱歉,您没有访问权限")));
    }
}
