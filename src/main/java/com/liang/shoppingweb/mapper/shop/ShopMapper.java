package com.liang.shoppingweb.mapper.shop;

import com.liang.shoppingweb.entity.shop.Shop;
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

    @Select("<script> select * from tbl_shop " +
            "where enable = '1' " +
            "<if test=\"type != null and '' != type \">and type = #{type} </if>" +
            "<if test=\"style != null and '' != style \">and style = #{style} </if>" +
            "<if test=\"keyword != null and '' != keyword \">and name like concat('%',#{keyword},'%') </if>" +
            "order by sales desc" +
            "</script>")
    List<Shop> getShopListBySearchInfo(SearchInfo searchInfo);

    @Update("update tbl_shop set sales = sales + #{param2} where id = #{param1}")
    void addSalesByShopId(String shopId, int sales);
}
