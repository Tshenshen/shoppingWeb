package com.liang.shoppingweb.entity.cart;

import com.liang.shoppingweb.entity.shop.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CartVo extends Cart {

    /**
     * 商品信息（根据GoodId获取）
     */
    private Goods goods ;

    public void setCartPro(Cart cart){
        setGoodsId(cart.getGoodsId());
        setGoodsNum(cart.getGoodsNum());
        setId(cart.getId());
        setUserId(getUserId());
    }
}
