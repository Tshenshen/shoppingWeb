package com.liang.shoppingweb.entity.order;

import com.liang.shoppingweb.entity.shop.Goods;
import com.liang.shoppingweb.entity.shop.ShopVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class OrderCellVo extends OrderCell{
    private ShopVo shopVo;
}
