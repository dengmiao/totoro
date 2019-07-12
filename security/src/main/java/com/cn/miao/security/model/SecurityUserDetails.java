package com.cn.miao.security.model;

import com.cn.miao.logic.domain.model.system.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @title: SecurityUserDetails
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 16:51
 **/
public class SecurityUserDetails extends SysUser implements UserDetails {

    public SecurityUserDetails(SysUser sysUser) {
        if(sysUser != null) {
            BeanUtils.copyProperties(sysUser, this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
