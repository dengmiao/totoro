package com.cn.miao.security.handler;

import cn.hutool.json.JSONUtil;
import com.cn.miao.common.model.Result;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.cn.miao.common.constant.CommonConstant.Security;

/**
 * @title: TotoroAuthenticationSuccessHandler
 * @description: 登录成功处理器
 * @author: dengmiao
 * @create: 2019-07-15 09:55
 **/
@Slf4j
@Component
public class TotoroAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${totoro.security.tokenExpireTime}")
    private Integer tokenExpireTime;

    /**
     * 登录校验成功 输入token
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
        List<String> list = authorities.stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
        final String token = Security.TOKEN_SPLIT + Jwts.builder()
                //主题 放入用户名
                .setSubject(username)
                //自定义属性 放入用户拥有请求权限
                .claim(Security.AUTHORITIES, JSONUtil.toJsonStr(list))
                //失效时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, Security.JWT_SIGN_KEY)
                .compact();
        Result.response(response, Result.ok(new HashMap<>(2){
            {
                put(Security.TOKEN_KEY, token);
            }
        }));
    }
}
