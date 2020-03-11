package com.liang.shoppingweb.mapper.shop;

import com.liang.shoppingweb.entity.shop.ShopItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShopItemMapper {

    @Insert("insert into tbl_shop_item(id, shop_id, name, price, stock, create_date) values(#{id}, #{shopId}, #{name}, #{price}, #{stock}, #{createDate}) ")
    void addNewShopItem(ShopItem shopItem);

    @Update("update tbl_shop_item set name = #{name}, price = #{price}, stock = #{stock}, update_date = #{updateDate} where id = #{id}")
    void updateShopItem(ShopItem shopItem);

    @Delete("delete from tbl_shop_item where id = #{id}")
    void deleteShopItem(String shopItemId);

//    @Insert("insert into tbl_shop_item(id, shop_id, name, price, stock, create_date) values ")
//    void batchAddNewShopItem(ShopItem shopItem);
}
