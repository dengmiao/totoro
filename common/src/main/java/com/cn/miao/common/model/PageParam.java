package com.cn.miao.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @title: PageParam
 * @description:
 * @author: dengmiao
 * @create: 2019-07-26 16:57
 **/
@Data
public class PageParam implements Serializable {

    private int pageNumber;

    private int pageSize;

    private String sort;

    private String order;
}
