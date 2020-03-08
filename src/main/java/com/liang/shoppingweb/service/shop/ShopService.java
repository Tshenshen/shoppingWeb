package com.liang.shoppingweb.service.shop;

import com.liang.shoppingweb.entity.shop.Shop;
import com.liang.shoppingweb.mapper.shop.ShopMapper;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ShopService {

    @Resource
    private ShopMapper shopMapper;

    public List<Shop> getShopListByEnterpriseId(){
        return shopMapper.getShopListByEnterpriseId(LoginUtils.getCurrentUserEnterpriseId());
    }

    public void createNewShop(Shop shop) {
        shop.setId(UUID.randomUUID().toString());
        shop.setEnterpriseId(LoginUtils.getCurrentUserEnterpriseId());
        shop.setCreateDate(new Date());
        shop.setEnable('1');
        shopMapper.createNewShop(shop);
    }

    public void updateShopEnable(Shop shop){
        shopMapper.updateShopEnable(shop);
    }

    public void deleteShopById(String id){
        shopMapper.deleteShopById(id);
    }
}
