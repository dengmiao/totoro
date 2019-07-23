package com.cn.miao.security.access;

/**
 * @title: PolicyEnforcement
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 16:03
 **/
public interface PolicyEnforcement {

    /**
     * 校验
     * @param subject
     * @param resource
     * @param action
     * @param environment
     * @return
     */
    boolean check(Object subject, Object resource, Object action, Object environment);
}
