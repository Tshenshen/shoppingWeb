package com.liang.shoppingweb.entity.order;

import com.liang.shoppingweb.entity.goods.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class OrderCellVo extends OrderCell{
    private Goods goods;
}
