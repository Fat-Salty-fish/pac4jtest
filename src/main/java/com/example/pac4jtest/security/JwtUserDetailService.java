package com.example.pac4jtest.security;

import com.example.pac4jtest.entity.UserInfo;
import com.example.pac4jtest.entity.UserInfoSimplify;
import com.example.pac4jtest.token.MyToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-19 11:18
 */
@Service
public class JwtUserDetailService implements UserDetailsService {
    @Autowired
    RedisTemplate redisTemplate;

    private MyToken myToken;

    static private Map<String, SimpleGrantedAuthority> authority = new HashMap();

    static {
        authority.put(null,new SimpleGrantedAuthority("NULL"));
        authority.put("Manager", new SimpleGrantedAuthority("ALL"));
        authority.put("Tester", new SimpleGrantedAuthority("TESTER"));
        authority.put("Salesman", new SimpleGrantedAuthority("SALESMAN"));
        authority.put("Repairman", new SimpleGrantedAuthority("REPAIRMAN"));
        authority.put("Storekeeper", new SimpleGrantedAuthority("STOREKEEPER"));
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //从redis中获取Token 并进行匹配
        String token = (String) redisTemplate.opsForValue().get("User:" + userName + ":Token");
        if (StringUtils.isBlank(token)) {
            throw new UsernameNotFoundException("Token验证错误 请重新登录");
        }
        //从Token中获取用户信息 用来权限认证
        UserInfoSimplify simplify = new UserInfoSimplify((Map)myToken.parseJWT(token).get("userInfo"));
        UserDetails userDetails = new Detail(simplify.getName(),token,Arrays.asList(authority.get(simplify.getPosition())));
        return userDetails;
    }
}
