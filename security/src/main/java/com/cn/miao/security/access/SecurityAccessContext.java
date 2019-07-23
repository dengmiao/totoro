package com.cn.miao.security.access;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @title: SecurityAccessContext
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 16:24
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SecurityAccessContext {

    private Object subject;
    private Object resource;
    private Object action;
    private Object environment;
}
