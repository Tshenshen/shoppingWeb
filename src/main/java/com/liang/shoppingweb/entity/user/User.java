package com.liang.shoppingweb.entity.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Date;


/**
 * 用户实体类
 */

@Data
public class User {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    @NotBlank
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
     * 余额
     */
    private double balance;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 1为有效
     */
    private char enable;
    /**
     * <@link>AuthorityConstant
     */
    private String role;
    /**
     * 最后登录时间
     */
    private Date lastLoginDate;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新日期
     */
    private Date updateDate;

}
