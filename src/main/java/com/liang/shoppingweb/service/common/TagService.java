package com.liang.shoppingweb.service.common;

import com.liang.shoppingweb.entity.common.Tag;
import com.liang.shoppingweb.mapper.common.TagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class TagService {

    @Resource
    private TagMapper tagMapper;

    public void addTapList(List<? extends Tag> tagList, String shopId) {
        if (tagList == null || tagList.isEmpty()) {
            return;
        }
        for (Tag tag : tagList) {
            tag.setShopId(shopId);
            tag.setId(UUID.randomUUID().toString());
        }
        tagMapper.addTapList(tagList);
    }

    public void deleteTapByShopId(String shopId) {
        tagMapper.deleteTapByShopId(shopId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTapList(List<? extends Tag> tagList, String shopId) {
        deleteTapByShopId(shopId);
        addTapList(tagList, shopId);
    }

    public List<Tag> getTagListByShopId(String shopId) {
        return tagMapper.getTagListByShopId(shopId);
    }
}
