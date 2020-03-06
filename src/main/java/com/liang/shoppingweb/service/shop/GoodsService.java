package com.liang.shoppingweb.service.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liang.shoppingweb.common.PageConstant;
import com.liang.shoppingweb.entity.shop.Goods;
import com.liang.shoppingweb.mapper.shop.GoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;



    public List<Goods> getAll(){
        return goodsMapper.getAll();
    }

    public PageInfo<Goods> getAllByPage(int pageNumber, int pageSize){
        PageHelper.startPage(pageNumber,pageSize);
        List<Goods> goods = goodsMapper.getAll();
        PageInfo<Goods> pageInfo = new PageInfo<>(goods,PageConstant.navigatePages);
        return pageInfo;
    }

    public Goods getGoodsById(String id) {
        return goodsMapper.getGoodsById(id);
    }
}
