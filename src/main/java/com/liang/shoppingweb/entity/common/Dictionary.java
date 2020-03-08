package com.liang.shoppingweb.entity.common;

import lombok.Data;

import java.util.Date;

@Data
public class Dictionary {

    /**
     * id
     */
    private String id;
    /**
     * 字典名
     */
    private String name;
    /**
     * 字典值
     */
    private String value;
    /**
     * 父类id
     */
    private String parentId;
    /**
     * 序号
     */
    private Integer order;
    /**
     * 创建日期
     */
    private Date createDate;
}
