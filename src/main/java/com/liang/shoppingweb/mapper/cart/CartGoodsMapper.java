package com.liang.shoppingweb.mapper.cart;

import com.liang.shoppingweb.entity.cart.CartVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartGoodsMapper {

    List<CartVo> getCartWithGoodsInfoByUserId(String userId);

    List<CartVo> getCartWithGoodsInfoByIds(String ids);

}
