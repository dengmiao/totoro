package com.cn.miao.security.filter;

import lombok.Getter;
import lombok.Setter;

/**
 * @title: AuthenticationBean
 * @description:
 * @author: dengmiao
 * @create: 2019-07-15 11:11
 **/
@Getter
@Setter
public class AuthenticationBean {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 记住我
     */
    private boolean rememberMe;

    /**
     * 登录类型 account
     */
    private String type;
}
