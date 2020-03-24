package com.liang.shoppingweb.mapper.shop;

import com.liang.shoppingweb.entity.shop.Shop;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopMapper {

    @Select("select * from tbl_shop where enterprise_id = #{enterpriseId} order by create_date asc")
    List<Shop> getShopListByEnterpriseId(String enterpriseId);

    @Select("select * from tbl_shop where id = #{id}")
    Shop getShopById(String id);

    @Insert("insert into tbl_shop(id, enterprise_id, name, images, type, style, `describe`, price, enable, create_date)" +
            "values(#{id}, #{enterpriseId}, #{name}, #{images}, #{type}, #{style}, #{describe}, #{price}, #{enable}, #{createDate})")
    void createNewShop(Shop shop);

    @Update("update tbl_shop set enable = #{enable}, update_date = #{updateDate} where id = #{id}")
    void updateShopEnable(Shop shop);

    @Delete("delete from tbl_shop where id = #{id}")
    void deleteShopById(String id);

    @Update("update tbl_shop set images = #{images}, update_date = #{updateDate} where id = #{id}")
    void updateShopImages(Shop shop);

    @Update("update tbl_shop set name = #{name}, type = #{type}, style= #{style}, `describe`= #{describe}, price= #{price}, update_date = #{updateDate} where id = #{id}")
    void updateShopInfoById(Shop shop);

    @Select("select * from tbl_shop where enable = '1' order by create_date asc")
    List<Shop> getShopListByPage();

    @Select("select s.* from tbl_shop s inner join tbl_collect c on s.id = c.shop_id where c.user_id = #{userId} order by create_date asc")
    List<Shop> getCollectShopListByPage(String userId);
}
