package com.liang.shoppingweb.mapper.shop;

import com.liang.shoppingweb.entity.shop.Shop;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShopMapper {

    @Select("select * from tbl_shop where enterprise_id = #{enterpriseId} order by create_date asc")
    List<Shop> getShopListByEnterpriseId(String enterpriseId);

    @Insert("insert into tbl_shop(id, enterprise_id, shop_name, images, type, style, `describe`, price, enable, create_date)" +
            "values(#{id}, #{enterpriseId}, #{shopName}, #{images}, #{type}, #{style}, #{describe}, #{price}, #{enable}, #{createDate})")
    void createNewShop(Shop shop);

    @Update("update tbl_shop set enable = #{enable} where id = #{id}")
    void updateShopEnable(Shop shop);

    @Update("delete from tbl_shop where id = #{id}")
    void deleteShopById(String id);
}
