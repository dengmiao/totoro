package com.cn.miao.security.access.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.expression.Expression;


/**
 * @title: PolicyRule
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 15:56
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PolicyRule {

    private String name;
    private String description;
    /**
     * Boolean SpEL expression. If evaluated to true, then this rule is applied to the request access context.
     */
    private Expression target;

    /**
     * Boolean SpEL expression, if evaluated to true, then access granted.
     */
    private Expression  condition;
}
