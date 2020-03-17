package com.liang.shoppingweb.entity.cart;

import com.liang.shoppingweb.entity.shop.ShopVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CartItemVo extends Cart {


    /**
     * 商品信息（根据GoodId获取）
     */
    private ShopVo shopVo;

    public void setCartPro(Cart cart) {
        setShopItemId(cart.getShopItemId());
        setShopItemNum(cart.getShopItemNum());
        setId(cart.getId());
        setUserId(getUserId());
    }

}
