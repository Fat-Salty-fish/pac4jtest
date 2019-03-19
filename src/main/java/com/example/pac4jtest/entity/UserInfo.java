package com.example.pac4jtest.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @auther 李忠杰
 * @create 2019-03-11 14:32
 */
@Getter
@Setter
public class UserInfo implements Serializable {
    private String userid;      //员工id
    private String unionid;     //员工在当前开发者企业账号内的唯一标识
    private String name;        //员工名字
    private String tel;         //员工分机号
    private String workPlace;   //员工办公地点
    private String remark;      //备注
    private String mobile;      //员工手机号
    private String email;       //员工电子邮箱
    private String orgEmail;    //员工企业邮箱
    private Boolean active;     //是否已经激活 true为已经激活 false为未激活
    //private Map<String,Integer> orderInDepts;//在对应的部门中的排序 key是部门id value是人员在这个部门中的排序值
    private Boolean isAdmin;    //是否为企业的管理员 true为是 false为否
    private Boolean isBoss;     //手否为企业的老板 true为是 false为否
    //private Map<String,Boolean> isLeaderInDepts;//在对应部门中是否为主管 key为部门id value是人员在这个部门中是否为主管 true表示是 false表示不是
    private Boolean isHide;     //是否号码隐藏 true表示隐藏 false表示不隐藏
    private List<Integer> department;   //成员所属部门id列表
    private String position;    //职位信息
    private String avatar;      //头像url
    private Date hiredDate;     //入职时间
    private String jobnumber;   //员工工号
    private Boolean isSenior;   //是否是高管

    @Override
    public String toString() {
        return "userid:" + this.userid +
                " username:" + this.name;
    }
}
