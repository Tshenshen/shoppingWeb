package com.liang.shoppingweb.mapper.cart;

import com.liang.shoppingweb.entity.cart.CartItemVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartItemVoMapper {

    List<CartItemVo> getCartWithGoodsInfoByUserId(String currentUserId);

    List<CartItemVo> getCartWithGoodsInfoByIds(String ids);
}
