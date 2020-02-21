package com.liang.shoppingweb.entity.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class OrderVo extends Order {
    private OrderCellVo[] orderCells;
}
