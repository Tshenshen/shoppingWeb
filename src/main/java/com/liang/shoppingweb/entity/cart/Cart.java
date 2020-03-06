package com.liang.shoppingweb.entity.cart;

import com.liang.shoppingweb.entity.order.OrderCell;
import lombok.Data;

import java.util.Date;

/**
 * 购物车实体类
 */

@Data
public class Cart {

    /**
     * 购物车编号
     */
    private String id;
    /**
     * 储存者id
     */
    private String userId;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品数量
     */
    private int goodsNum;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public OrderCell convertToOrderCell(){
        OrderCell orderCell = new OrderCell();
        orderCell.setGoodsId(this.getGoodsId());
        orderCell.setGoodsNum(this.getGoodsNum());
        return orderCell;
    }

}