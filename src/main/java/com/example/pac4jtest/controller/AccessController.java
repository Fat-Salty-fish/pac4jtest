package com.example.pac4jtest.controller;

import com.example.pac4jtest.service.AccessService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-11 14:17
 */
@RestController
@RequestMapping("/api/bas/test")
public class AccessController{
    @Autowired
    private AccessService accessService;

    @GetMapping("/login")
    public Object login(String code, HttpServletResponse response){
        return accessService.findUser(code,response);
    }



    @GetMapping("/helloWorld")
    @PreAuthorize("hasAuthority('all')")
    public Object hello() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
        return "helloWorld！";
    }

    @GetMapping("/error")
    public Object error(){
        Map<String,String> result = new HashMap<>();
        result.put("code", "2");
        result.put("msg","token错误 请重新登录");
        result.put("data",null);
        return result;
    }
}
