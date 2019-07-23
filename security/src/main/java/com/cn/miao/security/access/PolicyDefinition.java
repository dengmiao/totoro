package com.cn.miao.security.access;

import com.cn.miao.security.access.model.PolicyRule;

import java.util.List;

/**
 * @title: PolicyDefinition
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 16:01
 **/
public interface PolicyDefinition {

    /**
     * 获取所有
     * @return
     */
    List<PolicyRule> getAllPolicyRules();
}
