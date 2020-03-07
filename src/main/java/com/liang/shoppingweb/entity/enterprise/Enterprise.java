package com.liang.shoppingweb.entity.enterprise;

import lombok.Data;

import java.util.Date;

/**
 * 商家表
 */
@Data
public class Enterprise {
    /**
     * 商家id
     */
    private String id;
    /**
     * 绑定的用户
     */
    private String userId;
    /**
     * 商家名
     */
    private String enterpriseName;
    /**
     * 商家电话
     */
    private String phoneNumber;
    /**
     * 商家余额
     */
    private double balance;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
}
