package com.example.pac4jtest.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//此对象将保存在redis中
//用户进行登录时 凭借token解析 获取id 从redis中获取token 匹配通过后根据id获取用户信息
//得到的就是此对象

@Getter
@Setter
public class Detail implements UserDetails {
    private String userName;                    //用户名
    private String token ;                      //用户token
    private Collection<GrantedAuthority> authorities;

    public Detail(String userName,String token,Collection<GrantedAuthority> authorities){
        this.userName = userName;
        this.token = token;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return token;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
