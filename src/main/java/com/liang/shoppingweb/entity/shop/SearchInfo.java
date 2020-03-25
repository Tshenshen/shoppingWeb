package com.liang.shoppingweb.entity.shop;

import lombok.Data;

@Data
public class SearchInfo {
    private String type;
    private String style;
    private String keyword;
    private int pageNum;
}