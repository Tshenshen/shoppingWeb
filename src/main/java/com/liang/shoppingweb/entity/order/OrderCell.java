package com.liang.shoppingweb.entity.order;

import lombok.Data;

/**
 * 订单单元
 */

@Data
public class OrderCell {

    /**
     * 订单单元ID
     */
    private int id;
    /**
     * 总订单ID
     */
    private int orderId;
    /**
     * 商品id
     */
    private int goodsId;
    /**
     * 商品数量
     */
    private int goodsNum;

}
