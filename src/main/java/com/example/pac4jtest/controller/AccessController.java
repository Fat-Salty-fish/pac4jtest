package com.example.pac4jtest.controller;

import com.example.pac4jtest.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-11 14:17
 */
@RestController
public class AccessController{
    @Autowired
    private AccessService accessService;

    @GetMapping("/api/bas/test/login")
    public Object login(String code, HttpServletResponse response){
        return "helloWolrd!";
//        return accessService.login(code,response);
    }

    @GetMapping("/api/bas/test/helloWorld")
    public Object hello() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        System.out.println("123");
        return "helloWorld！";
    }

    @GetMapping("/api/bas/test/error")
    public Object error(){
        Map<String,String> result = new HashMap<>();
        result.put("code", "2");
        result.put("msg","token错误 请重新登录");
        result.put("data",null);
        return result;
    }

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public Object test(){
        return "跳转到了test接口？";
    }
}
