package com.cn.miao.base;

import com.cn.miao.common.model.Result;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @title: BaseController
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 14:53
 **/
public interface BaseController<E, ID extends Serializable> extends CommonController {

    /**
     * 获取注入的service
     * @return
     */
    BaseService<E, ID> getService();

    /**
     * 获取泛型的类模板对象
     * @param index
     * @return
     */
    default Class<E> getClazz(int index) {
        // 获取class上的泛型类型
        // Class<E> clazz = (Class <E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        // 获取interface上的泛型类型
        Type[] types = getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[index];
        Class<E> clazz = (Class<E>) parameterized.getActualTypeArguments()[index];

        return clazz;
    }

    default void page() {

    }

    default ResponseEntity<Result<?>> list() {
        return wrap(Result.ok());
    }
}
