package com.cn.miao.security.access;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: AbacPermissionEvaluator
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 16:12
 **/
@Slf4j
@Component
public class AbacPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private PolicyEnforcement policy;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        Object user = authentication.getPrincipal();
        Map<String, Object> environment = new HashMap<>(16);

        environment.put("time", new Date());

        log.debug("hasPermission({}, {}, {})", user, targetDomainObject, permission);
        return policy.check(user, targetDomainObject, permission, environment);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
