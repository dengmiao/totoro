package com.cn.miao.common.model;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @title: Result
 * @description: 公共rest接口返回
 * @author: dengmiao
 * @create: 2019-07-12 12:27
 **/
@Data
public class Result<T> {

    private transient HttpStatus status;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 返回处理消息
     */
    private String message;

    /**
     * 返回数据对象 data
     */
    private T result;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public static final HttpStatus OK = HttpStatus.OK;

    public static transient final HttpStatus NOTFOUND = HttpStatus.NOT_FOUND;

    public static transient final HttpStatus ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    public static Result<Object> ok(String msg) {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(OK.value());
        r.setMessage(msg);
        r.setStatus(OK);
        return r;
    }

    public static Result<Object> ok(Object obj) {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(OK.value());
        r.setMessage(OK.getReasonPhrase());
        r.setResult(obj);
        r.setStatus(OK);
        return r;
    }

    public static Result<Void> ok() {
        Result<Void> r = new Result<>();
        r.setSuccess(true);
        r.setCode(OK.value());
        r.setMessage(OK.getReasonPhrase());
        r.setStatus(OK);
        return r;
    }

    public static Result<?> notFound(String msg) {
        return error(NOTFOUND.value(), msg);
    }

    public static Result<Object> error(String msg) {
        return error(ERROR.value(), msg);
    }

    public static Result<Object> error(int code, String msg) {
        Result<Object> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        r.setStatus(ERROR);
        return r;
    }

    /**
     * 将Result包装成ResponseEntity
     * @param result
     * @return
     */
    public static ResponseEntity<Result<?>> wrapper(Result result) {
        return new ResponseEntity(result, result.getStatus());
    }
}
