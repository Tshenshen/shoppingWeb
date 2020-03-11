package com.liang.shoppingweb.mapper.shop;

import com.liang.shoppingweb.entity.shop.ShopVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopVoMapper {

    ShopVo getShopVoById(String id);
}
