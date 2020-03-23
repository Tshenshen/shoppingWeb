package com.liang.shoppingweb.service.user;

import com.liang.shoppingweb.entity.user.Collect;
import com.liang.shoppingweb.mapper.user.CollectMapper;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class CollectService {

    @Resource
    private CollectMapper collectMapper;

    public Collect getCollectByShopId(String shopId) {
        if (LoginUtils.getCurrentUser() == null) {
            return null;
        }
        Collect collect = new Collect();
        collect.setShopId(shopId);
        collect.setUserId(LoginUtils.getCurrentUserId());
        return collectMapper.getCollectByShopId(collect);
    }

    public Collect collectShop(Collect collect) {
        Collect oldCollect = getCollectByShopId(collect.getShopId());
        if (oldCollect != null) {
            return oldCollect;
        }
        collect.setId(UUID.randomUUID().toString());
        collect.setUserId(LoginUtils.getCurrentUserId());
        collect.setCollect('1');
        collectMapper.addCollect(collect);
        return collect;
    }

    public void cancelCollectShop(Collect collect) {
        if (!StringUtils.isEmpty(collect.getId())) {
            collectMapper.deleteCollectById(collect);
        }
        collect.setUserId(LoginUtils.getCurrentUserId());
        collectMapper.deleteCollectByShopId(collect);
    }
}
