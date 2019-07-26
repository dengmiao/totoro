package com.cn.miao.base;

import cn.hutool.core.util.StrUtil;
import com.cn.miao.common.model.PageParam;
import com.cn.miao.common.model.Result;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 成功
     * @param data
     * @return
     */
    default ResponseEntity<Result<?>> ok(Object data) {
        return wrap(Result.ok(data));
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    default ResponseEntity<Result<?>> fail(String msg) {
        return wrap(Result.error(msg));
    }

    /**
     * 失败
     * @param code
     * @param msg
     * @return
     */
    default ResponseEntity<Result<?>> fail(int code, String msg) {
        return wrap(Result.error(code, msg));
    }

    /**
     * 分页参数
     * @param page
     * @return
     */
    default Pageable initPageAble(PageParam page) {
        Pageable pageable;
        int pageNumber=page.getPageNumber();
        int pageSize=page.getPageSize();
        String sort=page.getSort();
        String order=page.getOrder();

        if(pageNumber<1){
            pageNumber=1;
        }
        if(pageSize<1){
            pageSize=10;
        }
        if(StrUtil.isNotBlank(sort)) {
            Sort.Direction d;
            if(StrUtil.isBlank(order)) {
                d = Sort.Direction.DESC;
            }else {
                d = Sort.Direction.valueOf(order.toUpperCase());
            }
            Sort s = new Sort(d,sort);
            pageable = PageRequest.of(pageNumber-1, pageSize,s);
        }else {
            pageable = PageRequest.of(pageNumber-1, pageSize);
        }
        return pageable;
    }
}
