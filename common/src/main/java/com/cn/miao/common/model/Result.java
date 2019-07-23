package com.cn.miao.common.model;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.cn.miao.common.constant.CommonConstant.HttpState.*;
import static com.cn.miao.common.constant.CommonConstant.Response;

/**
 * @title: Result
 * @description: 公共rest接口返回
 * @author: dengmiao
 * @create: 2019-07-12 12:27
 **/
@Data
public final class Result<T> {

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

    public static Result<?> bad_request(String msg) {
        return error(BAD_REQUEST.value(), msg);
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

    public static Result<?> method_not_allowed(String msg) {
        return error(METHOD_NOT_ALLOWED.value(), msg);
    }

    public static Result<?> unsupported_media_type(String msg) {
        return error(UNSUPPORTED_MEDIA_TYPE.value(), msg);
    }

    public static Result<?> error(String msg) {
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
            String str = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(result));
            // 输出字符串
            out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void response(ServletResponse response, ResponseEntity result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try(PrintWriter out = response.getWriter()) {
            String str = JSONUtil.formatJsonStr(JSONUtil.toJsonStr(result));
            // 输出字符串
            out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
