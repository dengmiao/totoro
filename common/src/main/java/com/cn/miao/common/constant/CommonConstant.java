package com.cn.miao.common.constant;

import org.springframework.http.HttpStatus;

/**
 * @title: CommonConstant
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 15:18
 **/
public interface CommonConstant {

    interface Response {
        String SUCCESS_MSG = "请求成功";
    }

    interface LoginError {
        String SYSTEM_ERROR = "系统错误";
        String USER_NOT_EXISTS = "用户不存在";
        String USERNAME_PASSWORD_ERROR = "密码错误";
        String USER_LOCKED = "账号被锁定";
    }

    interface Security {
        /**
         * token key
         */
        String TOKEN_KEY = "totoroToken";
        /**
         * token分割
         */
        String TOKEN_SPLIT = "Bearer ";

        /**
         * JWT签名加密key
         */
        String JWT_SIGN_KEY = "totoro";

        /**
         * token参数头
         */
        String HEADER = "X-Access-Token";

        /**
         * 权限参数头
         */
        String AUTHORITIES = "authorities";

        /**
         * 交互token前缀key
         */
        String TOKEN_PRE = "TOTORO_TOKEN_PRE:";

        /**
         * 用户token前缀key 单点登录使用
         */
        String USER_TOKEN = "TOTORO_USER_TOKEN:";
    }
    
    interface HttpState {
        /**
         * 200
         */
        HttpStatus OK = HttpStatus.OK;

        /**
         * 400
         */
        HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

        /**
         * 401
         */
        HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

        /**
         * 403
         */
        HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;

        /**
         * 404
         */
        HttpStatus NOTFOUND = HttpStatus.NOT_FOUND;

        /**
         * 405
         */
        HttpStatus METHOD_NOT_ALLOWED = HttpStatus.METHOD_NOT_ALLOWED;

        /**
         * 415
         */
        HttpStatus UNSUPPORTED_MEDIA_TYPE = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

        /**
         * 500
         */
        HttpStatus ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
