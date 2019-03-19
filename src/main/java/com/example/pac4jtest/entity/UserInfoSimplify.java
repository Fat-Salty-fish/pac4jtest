package com.example.pac4jtest.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-19 14:00
 */
@Getter
@Setter
public class UserInfoSimplify implements Serializable {
    private String userid;      //员工id
    private String name;        //员工名字
    private String mobile;      //员工手机号
    private String position;    //职位信息

    public UserInfoSimplify(UserInfo userInfo){
        this.userid = userInfo.getUserid();
        this.name = userInfo.getName();
        this.mobile = userInfo.getMobile();
        this.position = userInfo.getPosition();
    }

    public UserInfoSimplify(Map<String,String> userInfo){
        this.userid = userInfo.get("userid");
        this.name = userInfo.get("name");
        this.mobile = userInfo.get("mobile");
        this.position = userInfo.get("position");
    }

    @Override
    public String toString() {
        return "UserInfoSimplify{" +
                "userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
