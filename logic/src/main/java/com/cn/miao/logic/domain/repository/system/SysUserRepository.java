package com.cn.miao.logic.domain.repository.system;

import com.cn.miao.base.BaseRepository;
import com.cn.miao.logic.domain.model.system.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @title: SysUserRepository
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 16:34
 **/
@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Long> {

    /**
     * 按用户名查询
     * @param username
     * @return
     */
    SysUser findSysUserByUsername(String username);
}
