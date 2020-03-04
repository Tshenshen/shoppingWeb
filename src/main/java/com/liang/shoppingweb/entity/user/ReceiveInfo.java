package com.liang.shoppingweb.entity.user;


import lombok.Data;

import java.util.Date;

/**
 * 收件信息
 */

@Data
public class ReceiveInfo {
    /**
     * 收件信息id
     */
    private String id;
    /**
     * 绑定的用户名
     */
    private String userId;
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
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
}
