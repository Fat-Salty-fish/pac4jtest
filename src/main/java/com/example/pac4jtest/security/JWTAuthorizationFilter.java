package com.example.pac4jtest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @Description 用户认证拦截器
 * @auther 李忠杰
 * @create 2019-03-15 10:03
 */
public class JWTAuthorizationFilter extends AbstractAuthenticationProcessingFilter {
    private AuthenticationManager authenticationManager;

    protected JWTAuthorizationFilter(AuthenticationManager manager) {
        super(new AntPathRequestMatcher("/api/bas/test/**"));
        this.authenticationManager = manager;
    }

    //过滤方法 要获取请求中的token值
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {

        System.out.println("即将获取token值");
        String token = httpServletRequest.getHeader("token");

        if (StringUtils.isBlank(token)) {
            System.out.println("获取到的token值为空");
            throw new AuthenticationCredentialsNotFoundException("传入的token为空 无法认证");
        }
        System.out.println("获取到的Token值为" + token);
        TokenAuthentication authentication = new TokenAuthentication(token);
        return getAuthenticationManager().authenticate(authentication);
    }

//    认证成功时的操作
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        System.out.println("认证成功 即将创建用户进入SecurityContextHolder");
        super.successfulAuthentication(request,response,chain,authResult);
//        //这里会依然被这个filter截获 再次认证 如何解决这个问题呢
//        System.out.println(request.getRequestURL());

        //莫非是路径未被读取在缓存中？
        //好像是路径未被读取在缓存中 那么为什么我无法自定义实现跳转呢
//        SecurityContextHolder.getContext().setAuthentication(authResult);SecurityContextHolder.getContext().setAuthentication(authResult);
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());
        //这里如果这样写的话会一直重复进行 不妥当
//        String url = request.getRequestURL().toString();
//        response.sendRedirect(url);
        return ;
    }

//    认证失败时的操作
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        //设置响应码
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        //设置响应投
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String message;
        if (failed.getCause() != null) {
            message = failed.getCause().getMessage();
        } else {
            message = failed.getMessage();
        }
        byte[] body = new ObjectMapper().writeValueAsBytes(message);
        response.getOutputStream().write(body);
    }
}
