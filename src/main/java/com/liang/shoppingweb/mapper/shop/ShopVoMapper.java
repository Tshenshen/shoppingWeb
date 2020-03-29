package com.liang.shoppingweb.mapper.shop;

import com.liang.shoppingweb.entity.shop.ShopVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopVoMapper {

    ShopVo getShopVoById(String id);


    ShopVo getShopVoByItemId(String itemId);

    List<ShopVo> getShopWithTagListByEnterpriseId(String enterpriseId);
}
