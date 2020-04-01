package com.liang.shoppingweb.mapper.common;

import com.liang.shoppingweb.entity.common.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {

    @Insert("<script> insert into tbl_tag(id, dic_id, shop_id, name) values" +
            "<foreach item=\"item\" index=\"index\" separator=\",\" collection=\"list\">" +
            "(#{item.id}, #{item.dicId}, #{item.shopId}, #{item.name})" +
            "</foreach>" +
            "</script>")
    void addTapList(List<? extends Tag> tagList);

    @Delete("delete from tbl_tag where shop_id = #{shopId}")
    void deleteTapByShopId(String shopId);

    @Select("select * from tbl_tag where shop_id = #{shopId}")
    List<Tag> getTagListByShopId(String shopId);
}
