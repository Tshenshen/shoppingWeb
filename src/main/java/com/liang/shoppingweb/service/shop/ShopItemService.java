package com.liang.shoppingweb.service.shop;

import com.liang.shoppingweb.entity.shop.ShopItem;
import com.liang.shoppingweb.mapper.shop.ShopItemMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class ShopItemService {

    @Resource
    private ShopItemMapper shopItemMapper;

    public void addNewShopItem(ShopItem shopItem) {
        shopItem.setId(UUID.randomUUID().toString());
        shopItem.setCreateDate(new Date());
        shopItemMapper.addNewShopItem(shopItem);
    }

    public void updateShopItem(ShopItem shopItem) {
        shopItem.setUpdateDate(new Date());
        shopItemMapper.updateShopItem(shopItem);
    }

    public void deleteShopItem(String shopItemId) {
        shopItemMapper.deleteShopItem(shopItemId);
    }
}
