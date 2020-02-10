package com.liang.shoppingweb.entity.user;


import lombok.Data;

/**
 * 收件信息
 */

@Data
public class ReceiveInfo {
    /**
     * 收件信息id
     */
    private int id;
    /**
     * 绑定的用户id
     */
    private int userId;
    /**
     * 收货人名字
     */
    private String receiver;
    /**
     * 联系电话
     */
    private String phoneNumber;
    /**
     * 配送地址
     */
    private String address;
}
