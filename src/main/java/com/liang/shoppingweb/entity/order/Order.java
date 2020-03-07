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
    private String id;
    /**
     * 订购者用户名
     */
    private String userId;
    /**
     * 总价格
     */
    private double sumPrice;
    /**
     * 收件信息id
     */
    private String receiveInfoId;
    /**
     * 创建订单时间
     */
    private Date createDate;
    /**
     * 订单状态，1为待付款，2为待发货，3为待收货，4为完成，0为取消
     */
    private int state;
    /**
     * 订单状态更新时间
     */
    private Date updateDate;


}