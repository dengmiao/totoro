package com.cn.miao.base;

import com.cn.miao.common.model.Result;
import org.springframework.http.ResponseEntity;

/**
 * @title: CommonController
 * @description:
 * @author: dengmiao
 * @create: 2019-07-15 16:33
 **/
public interface CommonController {

    /**
     * 包装result
     * @param result
     * @return
     */
    default ResponseEntity<Result<?>> wrap(Result result) {
        return Result.wrap(result);
    }
}
