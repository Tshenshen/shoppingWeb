package com.liang.shoppingweb.mapper;

import com.liang.shoppingweb.entity.goods.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("select * from tbl_goods")
    List<Goods> getAll();
}
