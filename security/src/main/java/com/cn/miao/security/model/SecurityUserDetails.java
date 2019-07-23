package com.cn.miao.security.model;

import com.cn.miao.logic.domain.model.system.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        List<GrantedAuthority> authorityList = new ArrayList<>();
        // 权限集合

        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getStatus() != null && this.getStatus().intValue() == 1 ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus() != null && this.getStatus().intValue() == 1 ? true : false;
    }
}
