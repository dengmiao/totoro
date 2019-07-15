package com.cn.miao.security.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cn.miao.common.model.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.cn.miao.common.constant.CommonConstant.Security;

/**
 * @title: TotoroJwtAuthenticationFilter
 * @description:
 * @author: dengmiao
 * @create: 2019-07-15 14:45
 **/
@Slf4j
public class TotoroJwtAuthenticationFilter extends BasicAuthenticationFilter {

    private Boolean tokenRedis;

    private Integer tokenExpireTime;

    private StringRedisTemplate redisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    public TotoroJwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                         Boolean tokenRedis,
                                         Integer tokenExpireTime,
                                         StringRedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenRedis = tokenRedis;
        this.tokenExpireTime = tokenExpireTime;
        this.redisTemplate = redisTemplate;
    }

    public TotoroJwtAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(Security.HEADER);
        if(StrUtil.isBlank(header)) {
            header = request.getParameter(Security.HEADER);
        }
        Boolean notValid = StrUtil.isBlank(header) || (!tokenRedis && !header.startsWith(Security.TOKEN_SPLIT));
        if (notValid) {
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
            e.toString();
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header, HttpServletResponse response) {

        // 用户名
        String username = null;
        // 权限
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(tokenRedis){
            // redis
            String v = redisTemplate.opsForValue().get(Security.TOKEN_PRE + header);
            if(StrUtil.isBlank(v)){
                Result.response(response, Result.unauthorized("登录已失效，请重新登录"));
                return null;
            }
            try {
                TokenUser user = mapper.readValue(v, TokenUser.class);
                username = user.getUsername();
                for(String ga : user.getPermissions()){
                    authorities.add(new SimpleGrantedAuthority(ga));
                }
                if(tokenRedis && !user.getSaveLogin()){
                    // 若未保存登录状态重新设置失效时间
                    redisTemplate.opsForValue().set(Security.USER_TOKEN + username, v, tokenExpireTime, TimeUnit.MINUTES);
                    redisTemplate.opsForValue().set(Security.TOKEN_PRE, v, tokenExpireTime, TimeUnit.MINUTES);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // JWT
            try {
                // 解析token
                Claims claims = Jwts.parser()
                        .setSigningKey(Security.JWT_SIGN_KEY)
                        .parseClaimsJws(header.replace(Security.TOKEN_SPLIT, ""))
                        .getBody();

                //获取用户名
                username = claims.getSubject();
                //获取权限
                String authority = claims.get(Security.AUTHORITIES).toString();

                if(StrUtil.isNotBlank(authority)){
                    List<String> list = mapper.readValue(authority, new TypeReference<List<String>>() { });
                    for(String ga : list){
                        authorities.add(new SimpleGrantedAuthority(ga));
                    }
                }
            } catch (ExpiredJwtException e) {
                Result.response(response, Result.unauthorized("登录已失效，请重新登录"));
            } catch (Exception e){
                log.error(e.toString());
                Result.response(response, Result.error("解析token错误"));
            }
        }

        if(StrUtil.isNotBlank(username)) {
            //此处password不能为null
            User principal = new User(username, "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
        }
        return null;
    }
}
