package com.liang.shoppingweb.entity.user;

import lombok.Data;

/**
 * 收藏表
 */
@Data
public class Collect {

    private String id;
    private String userId;
    private String shopId;
    /**
     * 是否收藏，收藏为1，该字段目前多余
     */
    private char collect;
}
