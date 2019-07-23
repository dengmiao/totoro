package com.cn.miao.admin.web.handler;

import com.cn.miao.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MediaTypeNotSupportedStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @title: TotoroExceptionHandler
 * @description: 全局异常处理
 * @author: dengmiao
 * @create: 2019-07-16 08:58
 **/
@Slf4j
@RestControllerAdvice
public class TotoroExceptionHandler {

    /**
     * 捕获所有全局未知异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error("<Exception> {}, {}", request.getRequestURI(), e.getLocalizedMessage());
        return Result.wrap(Result.error(e.getMessage()));
    }

    /**
     * 参数绑定异常
     * @param e
     * @return
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Result<?>> handleBindException(WebExchangeBindException e) {
        return Result.wrap(Result.bad_request(toWebExchangeBindExceptionStr(e)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result<?>> auth(AccessDeniedException e) {
        return Result.wrap(Result.unauthorized(e.getMessage()));
    }

    /**
     * 处理404
     * 配置文件需增加 spring.mvc.throw-exception-if-no-handler-found=true和spring.resources.add-mappings=false
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Result<?>> handleNoHandlerFound(NoHandlerFoundException e, HttpServletRequest request) {
        log.error("<NoHandlerFoundException> {}, {}", e.getMessage(), request.getRequestURI());
        return Result.wrap(Result.notFound(e.getMessage()));
    }

    /**
     * 请求方式不支持
     * @param e
     * @param request
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result<?>> handleMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("<HttpRequestMethodNotSupportedException> {}, {}, {}", request.getRequestURI(), e.getMethod(), e.getSupportedHttpMethods());
        return Result.wrap(Result.method_not_allowed(toHttpRequestMethodNotSupportedExceptionStr(e)));
    }

    @ExceptionHandler(MediaTypeNotSupportedStatusException.class)
    public ResponseEntity<Result<?>> handleMediaTypeNotSupported(MediaTypeNotSupportedStatusException e) {
        return Result.wrap(Result.unsupported_media_type(toMediaTypeNotSupportedStatusException(e)));
    }

    private String toWebExchangeBindExceptionStr(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
    }

    private String toHttpRequestMethodNotSupportedExceptionStr(HttpRequestMethodNotSupportedException e) {
        StringBuffer sb = new StringBuffer(e.getMessage()).append(", try to use").append(e.getSupportedHttpMethods()).append(" to instead ").append(e.getMethod());
        return sb.toString();
    }

    private String toMediaTypeNotSupportedStatusException(MediaTypeNotSupportedStatusException e) {
        return new StringBuffer(e.getMessage()).append(", but supports ").append(e.getSupportedMediaTypes()).toString();
    }
}
