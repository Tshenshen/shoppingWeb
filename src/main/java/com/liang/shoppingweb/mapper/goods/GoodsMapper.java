package com.liang.shoppingweb.mapper.goods;

import com.liang.shoppingweb.entity.goods.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("select * from tbl_goods")
    List<Goods> getAll();

    @Select("select * from tbl_goods where id = #{id}")
    Goods getGoodsById(Integer id);

    @Update("update tbl_goods set stock = #{stock}, update_date = #{updateDate} where id = #{id}")
    void updateGoodsStock(Goods goods);
}
