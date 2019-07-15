package com.cn.miao.common.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.cn.miao.common.constant.CommonConstant.Response;

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

    public static transient final HttpStatus OK = HttpStatus.OK;

    public static transient final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    public static transient final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;

    public static transient final HttpStatus NOTFOUND = HttpStatus.NOT_FOUND;

    public static transient final HttpStatus ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    public static Result<Object> ok() {
        return ok(null, null);
    }

    public static Result<Object> ok(String msg) {
        return ok(msg, null);
    }

    public static Result<Object> ok(Object obj) {
        return ok(null, obj);
    }

    public static Result<Object> ok(String msg, Object value) {
        Result<Object> r = new Result<>();
        r.setSuccess(true);
        r.setCode(OK.value());
        r.setMessage(StrUtil.isNotBlank(msg) ? msg : Response.SUCCESS_MSG);
        r.setResult(value);
        r.setStatus(OK);
        return r;
    }

    public static Result<?> unauthorized(String msg) {
        return error(UNAUTHORIZED.value(), msg);
    }

    public static Result<?> forbidden(String msg) {
        return error(FORBIDDEN.value(), msg);
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
        r.setStatus(HttpStatus.valueOf(code));
        return r;
    }

    /**
     * 将Result包装成ResponseEntity
     * @param result
     * @return
     */
    public static ResponseEntity<Result<?>> wrap(Result result) {
        return new ResponseEntity(result, result.getStatus());
    }

    public static void response(ServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try(PrintWriter out = response.getWriter()) {
            out.println(wrap(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void response(ServletResponse response, ResponseEntity result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try(PrintWriter out = response.getWriter()) {
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
