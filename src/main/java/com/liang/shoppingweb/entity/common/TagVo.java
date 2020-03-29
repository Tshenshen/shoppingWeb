package com.liang.shoppingweb.entity.common;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TagVo extends Tag{
    private Dictionary dictionary;
}
