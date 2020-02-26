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
     * 订购者用户名
     */
    private String username;
    /**
     * 总价格
     */
    private double sumPrice;
    /**
     * 收件信息id
     */
    private int receiveInfoId;
    /**
     * 创建订单时间
     */
    private Date createDate;
    /**
     * 订单状态，0为取消，1为代付款，2为待收货，3为完成
     */
    private int state;
    /**
     * 订单状态更新时间
     */
    private Date updateDate;


}