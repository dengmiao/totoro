package com.cn.miao.security.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @title: TokenUser
 * @description:
 * @author: dengmiao
 * @create: 2019-07-15 15:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenUser {

    private String username;

    private List<String> permissions;

    private Boolean saveLogin;
}
