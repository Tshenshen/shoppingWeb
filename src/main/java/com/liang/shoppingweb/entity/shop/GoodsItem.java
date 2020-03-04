package com.liang.shoppingweb.entity.shop;

import lombok.Data;

import java.util.Date;

/**
 * 商品实体类
 */

@Data
public class GoodsItem {

    /**
     * 商品 id
     */
    private String id;
    /**
     * 店铺id
     */
    private String shopId;
    /**
     * 商品名字
     */
    private String name;
    /**
     * 商品描述
     */
    private String describe;
    /**
     * 商品 价格
     */
    private double price;
    /**
     * 库存 数量
     */
    private Integer stock;
    /**
     * 商品 上架时间
     */
    private Date createDate;
    /**
     * 商品 更新时间
     */
    private Date updateDate;

}