package com.cn.miao.common.constant;

/**
 * @title: CommonConstant
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 15:18
 **/
public interface CommonConstant {

    interface Response {
        String SUCCESS_MSG = "执行成功";
    }

    interface LoginError {
        String SYSTEM_ERROR = "系统错误";
        String USER_NOT_EXISTS = "用户不存在";
        String USERNAME_PASSWORD_ERROR = "密码错误";
        String USER_LOCKED = "账号被锁定";
    }
}
