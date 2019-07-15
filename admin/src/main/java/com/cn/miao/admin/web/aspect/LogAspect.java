package com.cn.miao.admin.web.aspect;

import com.cn.miao.common.toolkit.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @title: LogAspect
 * @description: 日志切面
 * @author: dengmiao
 * @create: 2019-07-15 16:20
 **/
@Slf4j
@Order(value = 0)
@Aspect
@Component
public class LogAspect {

    /**
     * 配置 controller 切面
     */
    @Pointcut(value = "execution(* *.cn.miao.*.*.*(..)) || execution(* *.cn.miao..*.controller.*.*(..)) || execution(* *.cn.miao.base.BaseController.*(..))")
    public void log() {

    }

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("log()")
    public void logDeBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("----------------------------------------------------------");
        log.info("用户代理:{}", UserAgentUtil.getUserAgent(request));
        log.info("请求路径:{}", request.getRequestURL().toString());
        log.info("请求类型:{}", request.getMethod());
        log.info("客户端IP:{}", request.getRemoteAddr());
        log.info("请求方法:{}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数:{}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 后置通知
     * @param returnValue
     */
    @AfterReturning(pointcut = "log()", returning = "returnValue")
    public void logDoAfterReturning(Object returnValue) {
        if (StringUtils.isEmpty(returnValue)) {
            returnValue = "";
        }
        log.info("请求响应:{}", returnValue);
        log.info("----------------------------------------------------------");
    }
}
