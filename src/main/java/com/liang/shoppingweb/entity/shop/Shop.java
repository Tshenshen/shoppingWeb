package com.liang.shoppingweb.entity.shop;

import lombok.Data;

import java.util.Date;

@Data
public class Shop {
    /**
     * 店铺id
     */
    private String id;
    /**
     * 商家id
     */
    private String enterpriseId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 商品描述
     */
    private String describe;
    /**
     * 最大价格
     */
    private double priceMax;
    /**
     * 最小价格
     */
    private double priceMin;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
}
