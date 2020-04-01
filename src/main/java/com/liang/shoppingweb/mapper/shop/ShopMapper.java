package com.liang.shoppingweb.mapper.shop;

import com.liang.shoppingweb.entity.shop.Shop;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.utils.SearchInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopMapper {

    @Select("select * from tbl_shop where enterprise_id = #{enterpriseId} order by create_date asc")
    List<Shop> getShopListByEnterpriseId(String enterpriseId);

    @Select("select * from tbl_shop where id = #{id}")
    Shop getShopById(String id);

    @Insert("insert into tbl_shop(id, enterprise_id, name, images, type, style, `describe`, price, enable, create_date, sales)" +
            "values(#{id}, #{enterpriseId}, #{name}, #{images}, #{type}, #{style}, #{describe}, #{price}, #{enable}, #{createDate}, 0)")
    void createNewShop(Shop shop);

    @Update("update tbl_shop set enable = #{enable}, update_date = #{updateDate} where id = #{id}")
    void updateShopEnable(Shop shop);

    @Delete("delete from tbl_shop where id = #{id}")
    void deleteShopById(String id);

    @Update("update tbl_shop set images = #{images}, update_date = #{updateDate} where id = #{id}")
    void updateShopImages(Shop shop);

    @Update("update tbl_shop set name = #{name}, type = #{type}, style= #{style}, `describe`= #{describe}, price= #{price}, update_date = #{updateDate} where id = #{id}")
    void updateShopInfoById(Shop shop);

    @Select("select * from tbl_shop where enable = '1' order by sales desc")
    List<Shop> getShopListByPage();

    @Select("select s.* from tbl_shop s inner join tbl_collect c on s.id = c.shop_id where c.user_id = #{userId} order by create_date asc")
    List<Shop> getCollectShopListByPage(String userId);

    @Select("<script> SELECT s.* FROM" +
            "(select * from tbl_shop " +
            "where enable = '1' " +
            "<if test=\"type != null and '' != type \">and type = #{type} </if>" +
            "<if test=\"style != null and '' != style \">and style = #{style} </if>" +
            "<if test=\"keyword != null and '' != keyword \">and name like concat('%',#{keyword},'%') </if>) s " +
            "${tagListQueryString} " +
            "order by s.sales desc" +
            "</script>")
    List<Shop> getShopListBySearchInfo(SearchInfo searchInfo);

    @Update("update tbl_shop set sales = sales + #{param2} where id = #{param1}")
    void addSalesByShopId(String shopId, int sales);

    @Select("<script> select * from tbl_shop where enable = '1' " +
            "<if test=\"styleIds != null\">" +
            "and style in ${styleIds} </if>" +
            "order by sales desc</script>")
    List<Shop> getShopListByStyleIds(String styleIds);

    @Select("SELECT s.* FROM tbl_shop s  " +
            "left JOIN " +
            "   ( SELECT t.shop_id, SUM( f.point ) point_sum FROM  " +
            "       ( SELECT * FROM tbl_favourite WHERE user_id = #{userId} ORDER BY point DESC LIMIT 7 ) f  " +
            "       INNER JOIN tbl_tag t ON t.dic_id = f.tag_dic_id " +
            "       GROUP BY t.shop_id " +
            "   ) i ON s.id = i.shop_id " +
            "WHERE s.`enable` = '1' " +
            "ORDER BY i.point_sum desc, s.sales desc" )
    List<Shop> getFavouriteShopList(String userId);
}
