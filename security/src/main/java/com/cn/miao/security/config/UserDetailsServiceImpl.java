package com.cn.miao.security.config;

import com.cn.miao.logic.domain.model.system.SysUser;
import com.cn.miao.logic.service.SysUserService;
import com.cn.miao.security.model.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @title: UserDetailsServiceImpl
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 16:46
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.findUserByUsername(username, false);
        return new SecurityUserDetails(sysUser);
    }
}
