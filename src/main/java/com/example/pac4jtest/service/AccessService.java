package com.example.pac4jtest.service;

import com.example.pac4jtest.entity.UserInfo;
import com.example.pac4jtest.entity.UserInfoSimplify;
import com.example.pac4jtest.token.MyToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description 用于处理免登陆操作的service
 * @auther 李忠杰
 * @create 2019-03-11 14:19
 */
@Service
public class AccessService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private MyToken myToken;

    //获取access_token以及使用access_token获取员工信息
    //获取员工信息之后 生成token 返回到前端 并将此token存入redis中
    //之后只需要判断传入的信息中是否有token 有token则验证token是否已经失效
    //若无Token则向钉钉发起请求 请求用户信息
    //此函数为用户首次登陆时 向钉钉发起请求的情况
    public String login(String code, HttpServletResponse response) {
        if(StringUtils.isBlank(code)){
            throw new NullPointerException("传入的code无效 无法访问 请传入有效的code值");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setName("李忠杰");
        userInfo.setUserid("1234567");
        userInfo.setPosition("Tester");
        UserInfoSimplify simplify = new UserInfoSimplify(userInfo);
        String token =myToken.createJWT(simplify);
        System.out.println(token);
        StringBuilder builder = new StringBuilder();
        builder.append("User:"+"lzj"+":Token");
        redisTemplate.opsForValue().set(builder.toString(),token);
        String result = (String)redisTemplate.opsForValue().get("User:"+"lzj"+":Token");
        System.out.println(new UserInfoSimplify((Map)myToken.parseJWT(result).get("userInfo")));

        response.setHeader("token",token);
        return token;
    }
}
