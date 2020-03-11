package com.liang.shoppingweb.service.shop;

import com.liang.shoppingweb.entity.shop.ShopVo;
import com.liang.shoppingweb.mapper.shop.ShopVoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ShopVoService {

    @Resource
    private ShopVoMapper shopVoMapper;

    public ShopVo getShopVoById(String shopId) {
        return shopVoMapper.getShopVoById(shopId);
    }
}
