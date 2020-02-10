package com.liang.shoppingweb.entity.user;

import lombok.Data;

import java.util.Date;


/**
 * 用户实体类
 */

@Data
public class User {
    /**
     * 用户id
     */
    private int id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 0:女  1:男
     */
    private char sex;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 1为删除
     */
    private char isDelete;
    /**
     * 1为卖家
     */
    private char isSeller;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 创建日期
     */
    private Date createDate;


}
