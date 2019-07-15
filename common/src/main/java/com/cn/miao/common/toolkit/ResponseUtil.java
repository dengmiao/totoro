package com.cn.miao.common.toolkit;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @title: ResponseUtil
 * @description: http响应工具类
 * @author: dengmiao
 * @create: 2019-07-15 10:11
 **/
@Slf4j
public class ResponseUtil {

    public static void outJson(ServletResponse response, JSONObject jsonObject) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try(PrintWriter out = response.getWriter()) {
            out.println(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outJson(ServletResponse response, Map<String, Object> resultMap) {
        outJson(response, new JSONObject(resultMap));
    }
}
