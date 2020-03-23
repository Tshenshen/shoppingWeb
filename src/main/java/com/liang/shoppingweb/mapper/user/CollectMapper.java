package com.liang.shoppingweb.mapper.user;

import com.liang.shoppingweb.entity.user.Collect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CollectMapper {

    @Select("select * from tbl_collect where user_id = #{userId} and shop_id = #{shopId}")
    Collect getCollectByShopId(Collect collect);

    @Insert("insert into tbl_collect(id, user_id, shop_id, collect) values(#{id}, #{userId}, #{shopId}, #{collect})")
    void addCollect(Collect collect);

    @Delete("delete from tbl_collect where user_id = #{userId} and shop_id = #{shopId}")
    void deleteCollectByShopId(Collect collect);

    @Delete("delete from tbl_collect where id = #{id}")
    void deleteCollectById(Collect collect);
}
