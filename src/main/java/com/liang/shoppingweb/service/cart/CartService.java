package com.liang.shoppingweb.service.cart;

import com.liang.shoppingweb.entity.cart.Cart;
import com.liang.shoppingweb.entity.cart.CartVo;
import com.liang.shoppingweb.entity.shop.ShopItem;
import com.liang.shoppingweb.mapper.cart.CartVoMapper;
import com.liang.shoppingweb.mapper.cart.CartMapper;
import com.liang.shoppingweb.service.shop.ShopItemService;
import com.liang.shoppingweb.utils.LoginUtils;
import com.liang.shoppingweb.utils.QueryPramFormatUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private CartVoMapper cartVoMapper;
    @Resource
    private ShopItemService shopItemService;

    public void addGoods(Cart newCart) throws Exception {
        //根据用户名和goodsId获取cart记录
        newCart.setUserId(LoginUtils.getCurrentUserId());
        Cart cart = cartMapper.getCartByUserIdAndShopItemId(newCart);
        //没有就插入新纪录
        if (cart == null) {
            newCart.setId(UUID.randomUUID().toString());
            newCart.setCreateDate(new Date());
            cartMapper.addGoods(newCart);
            return;
        }
        //如果有就更新数量
        ShopItem shopItem = shopItemService.getShopItemById(cart.getShopItemId());
        int newGoodsNum = cart.getShopItemNum() + newCart.getShopItemNum();
        if (newGoodsNum > shopItem.getStock()) {
            throw new Exception("库存不足");
        }
        cart.setUpdateDate(new Date());
        cart.setShopItemNum(newGoodsNum);
        cartMapper.updateCart(cart);
    }

    public CartVo buySingleGoods(Cart newCart) throws Exception {
        CartVo cartVo = new CartVo();
        ShopItem shopItem = shopItemService.getShopItemById(newCart.getShopItemId());
        cartVo.setShopItem(shopItem);
        //根据用户名和goodsId获取cart记录
        Cart cart = cartMapper.getCartByUserIdAndShopItemId(newCart);
        //没有就插入新纪录
        if (cart == null) {
            newCart.setId(UUID.randomUUID().toString());
            newCart.setCreateDate(new Date());
            cartMapper.addGoods(newCart);
            cartVo.setCartPro(newCart);
            return cartVo;
        }
        //如果有就更新数量
        int newGoodsNum = newCart.getShopItemNum();
        if (newGoodsNum > shopItem.getStock()) {
            throw new Exception("库存不足");
        }
        cart.setUpdateDate(new Date());
        cart.setShopItemNum(newGoodsNum);
        cartMapper.updateCart(cart);
        cartVo.setCartPro(cart);
        return cartVo;
    }


    public List<Cart> getCartsByUserId(String userId) {
        return cartMapper.getCartsByUserId(userId);
    }

    public List<CartVo> getCartWithGoodsInfoByUserId() {
        return cartVoMapper.getCartWithGoodsInfoByUserId(LoginUtils.getCurrentUserId());
    }

    public void deleteCartItem(String id) {
        cartMapper.deleteItem(id);
    }

    public void deleteCartItems(String[] itemIds) {
        String ids = QueryPramFormatUtils.strToIn(itemIds);
        cartMapper.deleteItems(ids);
    }

    public void updateItemNum(Cart cart) {
        cartMapper.updateCart(cart);
    }
}
