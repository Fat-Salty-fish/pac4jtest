package com.example.pac4jtest.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-13 15:14
 */
@Getter
@Setter
public class AuthenticationToken extends AbstractAuthenticationToken {
    private String userId;      //用户id
    private String userName;    //用户名字
    private String position;    //用户岗位(理解为权限?)

    private String token;       //传入的token

    //在认证之前 被截获
    public AuthenticationToken(String token) {
        super(null);
        System.out.println("正在创建认证对象！");
        this.token = token;
    }

    //在认证之后 返回此对象 此时对象已经被验证 得到了详细信息
    public AuthenticationToken(String userId, String userName, String position, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
        this.userName = userName;
        this.position = position;
        super.setAuthenticated(true);
        System.out.println("你现在已经登录成功了耶！有了一定的权限 不知道下一步该怎么做了耶！");
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }
}
