package com.example.pac4jtest.controller;

import com.example.pac4jtest.service.AccessService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    /**
     * 返回用户信息接口 传入用户免登码 然后先获取access_token 再通过access_token和免登码获取用户信息
     * @param code
     * @return
     */
    @GetMapping("/login")
//    public Object login(String code,HttpServletResponse response){
//            return accessService.findUser(code,response);
//}
    public Object login(){
        return "error";
    }



    @GetMapping("/helloWorld")
    public Object hello() {
        return "helloWorld！";
    }

    @GetMapping("/error")
    public Object error(){
        return "error!";
    }
}
