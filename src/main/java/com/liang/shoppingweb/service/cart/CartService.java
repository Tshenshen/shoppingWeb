package com.liang.shoppingweb.service.cart;

import com.liang.shoppingweb.entity.cart.Cart;
import com.liang.shoppingweb.entity.cart.CartItemVo;
import com.liang.shoppingweb.entity.cart.CartShopVo;
import com.liang.shoppingweb.mapper.cart.CartItemVoMapper;
import com.liang.shoppingweb.mapper.cart.CartMapper;
import com.liang.shoppingweb.mapper.cart.CartShopVoMapper;
import com.liang.shoppingweb.mapper.cart.CartVoMapper;
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
    @Resource
    private CartItemVoMapper cartItemVoMapper;
    @Resource
    private CartShopVoMapper cartShopVoMapper;


    public void addGoods(Cart newCart) {
        addOrUpdateCart(newCart);
    }

    public Cart buySingleGoods(Cart newCart) {
        return addOrUpdateCart(newCart, true);
    }


    private void addOrUpdateCart(Cart newCart) {
        addOrUpdateCart(newCart, false);
    }

    private Cart addOrUpdateCart(Cart newCart, boolean forBuy) {
        //根据用户名和goodsId获取cart记录
        newCart.setUserId(LoginUtils.getCurrentUserId());
        Cart cart = cartMapper.getCartByUserIdAndShopItemId(newCart);
        //没有就插入新纪录
        if (cart == null) {
            newCart.setId(UUID.randomUUID().toString());
            newCart.setCreateDate(new Date());
            cartMapper.addGoods(newCart);
            return newCart;
        }
        //如果有就更新数量
        //检查库存
//        ShopItem shopItem = shopItemService.getShopItemById(cart.getShopItemId());
//        int newGoodsNum = cart.getShopItemNum() + newCart.getShopItemNum();
//        if (newGoodsNum > shopItem.getStock()) {
//            throw new Exception("库存不足");
//        }
        int newGoodsNum = forBuy ? newCart.getShopItemNum() : cart.getShopItemNum() + newCart.getShopItemNum();
        cart.setUpdateDate(new Date());
        cart.setShopItemNum(newGoodsNum);
        cartMapper.updateCart(cart);
        return cart;
    }


    public List<Cart> getCartsByUserId(String userId) {
        return cartMapper.getCartsByUserId(userId);
    }

    public List<CartItemVo> getCartWithGoodsInfoByUserId() {
        return cartItemVoMapper.getCartWithGoodsInfoByUserId(LoginUtils.getCurrentUserId());
    }

    public List<CartShopVo> getCartShopVoListByUserId() {
        return cartShopVoMapper.getCartShopVoListByUserId(LoginUtils.getCurrentUserId());
    }

    public void deleteCartItem(String id) {
        cartMapper.deleteItem(id);
    }

    public void deleteCartItems(String[] itemIds) {
        String ids = QueryPramFormatUtils.arrayToIn(itemIds);
        cartMapper.deleteItems(ids);
    }

    public void updateItemNum(Cart cart) {
        cartMapper.updateCart(cart);
    }

    public List<CartItemVo> getCartWithGoodsInfoByIds(String ids) {
        String in_ids = QueryPramFormatUtils.strToIn(ids, ",");
        return cartItemVoMapper.getCartWithGoodsInfoByIds(in_ids);
    }
}
