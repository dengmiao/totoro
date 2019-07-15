package com.cn.miao.admin.web.controller;

import com.cn.miao.common.model.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: SecurityController
 * @description:
 * @author: dengmiao
 * @create: 2019-07-15 15:53
 **/
@RestController
@RequestMapping("security")
public class SecurityController {

    @GetMapping("needLogin")
    public ResponseEntity<Result<?>> needLogin() {
        return Result.wrap(Result.unauthorized("您还未登录"));
    }
}
