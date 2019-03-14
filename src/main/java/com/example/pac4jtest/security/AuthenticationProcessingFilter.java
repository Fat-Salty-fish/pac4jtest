package com.example.pac4jtest.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-13 15:22
 */
public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    //设置截获的路径
    AuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/**"));
    }

    //截获请求之后获取获取token信息 封装成AuthenticationToken对象
    //通过AuthenticationManager认证用户是否登录以及权限
    //认证成功之后 AuthenticationManager会返回一个充满了信息的Authentication对象 此时为AuthenticationToken对象
    //SecurityContextHolder安全上下文会持有这个对象
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        //获取token用于验证用户是否已经登录
        System.out.println("已经截获了这个请求");
        String token = httpServletRequest.getHeader("token");
        return getAuthenticationManager().authenticate(new AuthenticationToken(token));
    }
}
