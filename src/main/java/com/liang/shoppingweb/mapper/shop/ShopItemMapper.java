package com.liang.shoppingweb.mapper.shop;

import com.liang.shoppingweb.entity.shop.ShopItem;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ShopItemMapper {

    @Insert("insert into tbl_shop_item(id, shop_id, name, price, stock, create_date) values(#{id}, #{shopId}, #{name}, #{price}, #{stock}, #{createDate}) ")
    void addNewShopItem(ShopItem shopItem);

    @Update("update tbl_shop_item set name = #{name}, price = #{price}, stock = #{stock}, update_date = #{updateDate} where id = #{id}")
    void updateShopItem(ShopItem shopItem);

    @Delete("delete from tbl_shop_item where id = #{shopItemId}")
    void deleteShopItem(String shopItemId);

    @Select("select * from tbl_shop_item where id = ${shopItemId}")
    ShopItem getShopItemById(String shopItemId);

    @Update("update tbl_shop_item set stock = #{stock}, update_date = #{updateDate} where id = #{id}")
    void updateShopItemStock(ShopItem shopItem);

//    @Insert("insert into tbl_shop_item(id, shop_id, name, price, stock, create_date) values ")
//    void batchAddNewShopItem(ShopItem shopItem);
}
