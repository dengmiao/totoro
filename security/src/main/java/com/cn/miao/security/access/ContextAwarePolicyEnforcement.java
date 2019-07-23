package com.cn.miao.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: ContextAwarePolicyEnforcement
 * @description: Use this class in any of the Spring Beans to evaluate security policy.
 * @author: dengmiao
 * @create: 2019-07-22 16:36
 **/
@Component
public class ContextAwarePolicyEnforcement {

    @Autowired
    protected PolicyEnforcement policy;

    public void checkPermission(Object resource, String permission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> environment = new HashMap<>(16);

        environment.put("time", new Date());

        if(!policy.check(auth.getPrincipal(), resource, permission, environment)) {
            throw new AccessDeniedException("Access is denied");
        }
    }
}
