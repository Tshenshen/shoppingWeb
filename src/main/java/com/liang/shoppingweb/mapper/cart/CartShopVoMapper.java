package com.liang.shoppingweb.mapper.cart;

import com.liang.shoppingweb.entity.cart.CartShopVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartShopVoMapper {

    List<CartShopVo> getCartShopVoListByUserId(String currentUserId);

    List<CartShopVo> getCartShopVoListByIds(String ids);

}
