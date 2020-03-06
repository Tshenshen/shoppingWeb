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
     * 商品id
     */
    private String goodsId;
    /**
     * 商家名
     */
    private String enterpriseName;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
}
