package com.liang.shoppingweb.service.cart;

import com.liang.shoppingweb.common.AuthorityConstant;
import com.liang.shoppingweb.entity.cart.Cart;
import com.liang.shoppingweb.entity.cart.CartVo;
import com.liang.shoppingweb.entity.shop.Goods;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.cart.CartGoodsMapper;
import com.liang.shoppingweb.mapper.cart.CartMapper;
import com.liang.shoppingweb.mapper.goods.GoodsMapper;
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
    private CartGoodsMapper cartGoodsMapper;
    @Resource
    private GoodsMapper goodsMapper;

    public void addGoods(Cart newCart) throws Exception {
        //根据用户名和goodsId获取cart记录
        Cart cart = cartMapper.getCartByUserIdAndGoodsId(newCart);
        //没有就插入新纪录
        if (cart == null) {
            newCart.setId(UUID.randomUUID().toString());
            newCart.setCreateDate(new Date());
            cartMapper.addGoods(newCart);
            return;
        }
        //如果有就更新数量
        Goods goods = goodsMapper.getGoodsById(cart.getGoodsId());
        int newGoodsNum = cart.getGoodsNum()+ newCart.getGoodsNum();
        if (newGoodsNum > goods.getStock()){
            throw new Exception("库存不足");
        }
        cart.setUpdateDate(new Date());
        cart.setGoodsNum(newGoodsNum);
        cartMapper.updateCart(cart);
    }

    public CartVo buySingleGoods(Cart newCart) throws Exception {
        CartVo cartVo = new CartVo();
        Goods goods = goodsMapper.getGoodsById(newCart.getGoodsId());
        cartVo.setGoods(goods);
        //根据用户名和goodsId获取cart记录
        Cart cart = cartMapper.getCartByUserIdAndGoodsId(newCart);
        //没有就插入新纪录
        if (cart == null) {
            newCart.setId(UUID.randomUUID().toString());
            newCart.setCreateDate(new Date());
            cartMapper.addGoods(newCart);
            cartVo.setCartPro(newCart);
            return cartVo;
        }
        //如果有就更新数量
        int newGoodsNum = newCart.getGoodsNum();
        if (newGoodsNum > goods.getStock()){
            throw new Exception("库存不足");
        }
        cart.setUpdateDate(new Date());
        cart.setGoodsNum(newGoodsNum);
        cartMapper.updateCart(cart);
        cartVo.setCartPro(cart);
        return cartVo;
    }


    public List<Cart> getCartsByUserId(String userId) {
        return cartMapper.getCartsByUserId(userId);
    }

    public List<CartVo> getCartWithGoodsInfoByUserId() {
        List<CartVo> carts = cartGoodsMapper.getCartWithGoodsInfoByUserId(LoginUtils.getCurrentUserId());
        return carts;
    }

    public void deleteCartItem(Integer id) {
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
