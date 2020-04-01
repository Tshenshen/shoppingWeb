package com.liang.shoppingweb.mapper.user;

import com.liang.shoppingweb.entity.user.Favourite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FavouriteMapper {

    @Insert("<script> " +
            "insert into tbl_favourite(id, user_id, tag_id, point) " +
            "values " +
            "<foreach collection=\"list\" item=\"favourite\" separator=\",\"> " +
            "(#{favourite.id}, #{favourite.userId}, #{favourite.tagId}, #{favourite.point}) " +
            "</foreach> " +
            "ON DUPLICATE KEY UPDATE " +
            "point = point + 1 " +
            "</script>")
    void insertOrUpdateFavouriteList(List<Favourite> favouriteList);

    @Update("update tbl_favourite set point = point - 1 where " +
            "user_id = #{arg1} and " +
            "tag_id in (select id from tbl_tag where shop_id = #{arg0}) ")
    void cancelFavouriteByShopId(String shopId, String userId);
}
