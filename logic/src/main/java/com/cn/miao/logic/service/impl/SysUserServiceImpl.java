package com.cn.miao.logic.service.impl;

import com.cn.miao.base.BaseRepository;
import com.cn.miao.logic.domain.model.system.SysUser;
import com.cn.miao.logic.domain.repository.system.SysUserRepository;
import com.cn.miao.logic.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title: SysUserServiceImpl
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 16:36
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public BaseRepository<SysUser, Long> getRepository() {
        return this.sysUserRepository;
    }

    @Override
    public SysUser findUserByUsername(String username, boolean delStatus) {
        return sysUserRepository.findSysUserByUsername(username);
    }
}
