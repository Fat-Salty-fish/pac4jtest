package com.example.pac4jtest.security;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
        AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
        //获取token
        String token = authenticationToken.getToken();
        //如果token为空 则用户需要登录 登录时需要携带code 直接跳转到错误界面即可 让前端调用登录界面
        //通过上面的接口获取token 再进行验证
        //当token不为空时 因为是Token 所以将token先解析 解析之后获取用户信息 根据用户信息获取redis里的token
        //与传入的token进行匹配 如果匹配则登录成功并赋予权限 如果匹配不成功则登录不成功
        System.out.println("匹配到了Authentication这里啦~");
        if(token == null){
            System.out.println("你的token是空的！你真是个弟弟啊！");
            throw new BadCredentialsException("输入的token为空 访问失败");
        }else {
            if("123456".equals(token)){
                System.out.println("你的token是正确的哦");
                return new AuthenticationToken(token,"李忠杰","sit", Arrays.asList(authorityMap.get("1")));
            }
            throw new BadCredentialsException("输入的token错误或已经失效 请重新登录");
        }
    }

    //只支持AuthenticationToken这个类来验证身份
    @Override
    public boolean supports(Class<?> authentication) {
        return (AuthenticationToken.class.isAssignableFrom(authentication));
    }
}
