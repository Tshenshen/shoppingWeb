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
    private String name;
    /**
     * 商品描述
     */
    private String describe;
    /**
     * 商品种类
     */
    private String type;
    /**
     * 商品类型
     */
    private String style;
    /**
     * 价格区间
     */
    private String price;
    /**
     * 图片id串
     */
    private String images = "";
    /**
     * 启用为1
     */
    private char enable;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;
}
