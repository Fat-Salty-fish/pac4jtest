package com.example.pac4jtest.security;

import com.example.pac4jtest.entity.UserInfoSimplify;
import com.example.pac4jtest.token.MyToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 此filter用来验证用户是否登录过 验证token的有效性
 * @auther 李忠杰
 * @create 2019-03-19 14:24
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    //用来存放权限
    static private Map<String, SimpleGrantedAuthority> authority = new HashMap();
    static {
        authority.put(null,new SimpleGrantedAuthority("NULL"));
        authority.put("Manager", new SimpleGrantedAuthority("ALL"));
        authority.put("Tester", new SimpleGrantedAuthority("TESTER"));
        authority.put("Salesman", new SimpleGrantedAuthority("SALESMAN"));
        authority.put("Repairman", new SimpleGrantedAuthority("REPAIRMAN"));
        authority.put("Storekeeper", new SimpleGrantedAuthority("STOREKEEPER"));
    }
    //用来解析token
    private MyToken myToken ;

    public JwtTokenFilter (MyToken token){
        this.myToken = token;
    }

    //进行对请求的解析
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String headerToken = httpServletRequest.getHeader("token");
        if(StringUtils.isBlank(headerToken)){
            throw new BadCredentialsException("未传入token 请登录后操作");
        }
        String userName = (String)myToken.parseJWT(headerToken).get("name");
        //传入service中
        if(StringUtils.isNotBlank(headerToken)&& StringUtils.isNotBlank(userName)){
            //从redis中获取Token 并进行匹配
            String token = (String) redisTemplate.opsForValue().get("User:" + userName + ":Token");
            if (StringUtils.isBlank(token)) {
                throw new UsernameNotFoundException("Token验证错误 请重新登录");
            }
            if(headerToken.equals(token) && SecurityContextHolder.getContext().getAuthentication() == null){
                //从Token中获取用户信息 用来权限认证
                UserInfoSimplify simplify = new UserInfoSimplify((Map)myToken.parseJWT(token).get("userInfo"));
                UserDetails userDetails = new Detail(simplify.getName(),token, Arrays.asList(authority.get(simplify.getPosition())));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
