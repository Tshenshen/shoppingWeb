package com.liang.shoppingweb.entity.user;

import lombok.Data;

import java.util.UUID;

/**
 * 记录用户对标签的喜爱程度
 */

@Data
public class Favourite {

    private String id;
    private String userId;
    private String tagId;
    /**
     * 标签的分数
     */
    private int point;

    public Favourite() {
    }

    public Favourite(String userId, String tagId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.tagId = tagId;
        this.point = 1;
    }
}
