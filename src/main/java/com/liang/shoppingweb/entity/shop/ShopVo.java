package com.liang.shoppingweb.entity.shop;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ShopVo extends Shop{
    private List<ShopItem> shopItems;
}
