package com.liang.shoppingweb.entity.cart;

import com.liang.shoppingweb.entity.shop.Shop;
import com.liang.shoppingweb.entity.user.Collect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class CartShopVo extends Shop {

    private Collect collect;
    private List<CartVo> cartVoList;

}
