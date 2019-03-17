package com.example.pac4jtest.security;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-13 15:27
 */
public class MyAuthenticationProvider implements AuthenticationProvider{

    private static final Map<String, SimpleGrantedAuthority> authorityMap = new ConcurrentHashMap<>();

    //模拟权限
    static{
        authorityMap.put("1",new SimpleGrantedAuthority("all"));
        authorityMap.put("2",new SimpleGrantedAuthority("test"));
    }

    //在这个方法里对token进行验证
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //对token进行验证 从redis中获取token 与这个token进行对比 如果对比相同则成功 如果对比不同则失败
        System.out.println("要对token进行验证了");
        TokenAuthentication auth = (TokenAuthentication)authentication;
        if(auth==null){
            throw new BadCredentialsException("发生了null错误");
        }
        if(auth != null && auth.isAuthenticated()){
            System.out.println("这个认证已经被认证过了");
            TokenAuthentication result = new TokenAuthentication(auth.getUserId(),auth.getUserName(),auth.getPosition(),Arrays.asList(authorityMap.get("1")));
            result.setDetails(null);
            return result;
        }
        String token = (String)auth.getPrincipal();
        if ("123456".equals(token)){
            System.out.println("Token认证成功 token为123456");
            TokenAuthentication result = new TokenAuthentication("123454321","李忠杰","老板",Arrays.asList(authorityMap.get("1")));
            Detail detail = new Detail();
            detail.setUserName("李忠杰");
            detail.setToken(token);
            detail.setAuthorities(Arrays.asList(authorityMap.get("1")));
//            result.setDetails(detail);
            return result;
        }else {
            throw new BadCredentialsException("token码错误 请重新登录");
        }
    }

    //支持AuthenticationToken这个类来验证身份
    @Override
    public boolean supports(Class<?> authentication) {
        return (TokenAuthentication.class.isAssignableFrom(authentication));
    }
}
