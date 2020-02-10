package com.liang.shoppingweb.entity.order;

import lombok.Data;

import java.util.Date;

/**
 * 订单实体类
 */

@Data
public class Order {

    /**
     * 订单id
     */
    private int id;
    /**
     * 订购者id
     */
    private int userId;
    /**
     * 收件信息id
     */
    private int receiveInfoId;
    /**
     * 创建订单时间
     */
    private Date createDate;
    /**
     * 订单状态，0为未完成，1为完成，2为取消
     */
    private char state;
    /**
     * 订单状态更新时间
     */
    private Date updateDate;


}