package com.liang.shoppingweb.entity.cart;

import lombok.Data;

import java.util.Date;

/**
 * 购物车实体类
 */

@Data
public class Cert {

    /**
     * 购物车编号
     */
    private int id;
    /**
     * 储存者id
     */
    private int userId;
    /**
     * 商品id
     */
    private int goodsId;
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


}