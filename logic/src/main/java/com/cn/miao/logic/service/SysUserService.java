package com.cn.miao.logic.service;

import com.cn.miao.base.BaseService;
import com.cn.miao.logic.domain.model.system.SysUser;

/**
 * @title: SysUserService
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 16:35
 **/
public interface SysUserService extends BaseService<SysUser, Long> {

    /**
     * 按用户名查询
     * @param username
     * @param delStatus
     * @return
     */
    SysUser findUserByUsername(String username, boolean delStatus);
}
