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
    private String id;
    /**
     * 总订单ID
     */
    private String orderId;
    /**
     * 商品id
     */
    private String shopItemId;
    /**
     * 商品数量
     */
    private int shopItemNum;

}
