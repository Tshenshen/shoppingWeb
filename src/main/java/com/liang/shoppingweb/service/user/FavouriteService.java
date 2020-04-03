package com.liang.shoppingweb.service.user;

import com.liang.shoppingweb.entity.common.Tag;
import com.liang.shoppingweb.entity.user.Favourite;
import com.liang.shoppingweb.mapper.user.FavouriteMapper;
import com.liang.shoppingweb.service.common.TagService;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteService {

    @Resource
    private FavouriteMapper favouriteMapper;
    @Autowired
    private TagService tagService;

    public List<Favourite> tagListToFavouriteList(List<Tag> tagList, String userId) {
        List<Favourite> favouriteList = new ArrayList<>();
        for (Tag tag : tagList) {
            favouriteList.add(new Favourite(userId, tag.getDicId()));
        }
        return favouriteList;
    }

    public void updateWhenPay(List<String> shopIds) {
        String userId = LoginUtils.getCurrentUserId();
        new Thread(() -> {
            List<Tag> tagList = new ArrayList<>();
            for (String shopId : shopIds) {
                tagList.addAll(tagService.getTagListByShopId(shopId));
            }
            insertOrUpdateFavouriteList(tagListToFavouriteList(tagList, userId));
        }).start();
    }

    public void updateWhenCollect(String shopId) {
        String userId = LoginUtils.getCurrentUserId();
        new Thread(() -> insertOrUpdateFavouriteList(tagListToFavouriteList(tagService.getTagListByShopId(shopId), userId))).start();
    }

    public void updateWhenCancelCollect(String shopId) {
        String userId = LoginUtils.getCurrentUserId();
        new Thread(() -> favouriteMapper.cancelFavouriteByShopId(shopId, userId)).start();
    }

    public void insertOrUpdateFavouriteList(List<Favourite> favouriteList){
        if (favouriteList != null && favouriteList.size() > 0) {
            favouriteMapper.insertOrUpdateFavouriteList(favouriteList);
        }
    }
}
