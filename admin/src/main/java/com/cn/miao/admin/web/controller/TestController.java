package com.cn.miao.admin.web.controller;

import com.cn.miao.base.CommonController;
import com.cn.miao.common.model.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: TestController
 * @description:
 * @author: dengmiao
 * @create: 2019-07-15 16:30
 **/
@RestController
@RequestMapping("test")
public class TestController implements CommonController {

    @GetMapping("ok")
    public ResponseEntity<Result<?>> ok() {
        return wrap(Result.ok());
    }

    @GetMapping("error")
    public ResponseEntity<Result<?>> error() {
        return wrap(Result.error("未知错误,请求失败"));
    }
}
