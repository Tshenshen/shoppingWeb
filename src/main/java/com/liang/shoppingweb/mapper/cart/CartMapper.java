package com.liang.shoppingweb.mapper.cart;

import com.liang.shoppingweb.entity.cart.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("insert into tbl_cart(id,user_id,shop_item_id,shop_item_num,create_date) values(#{id},#{userId},#{shopItemId},#{shopItemNum},#{createDate})")
    void addGoods(Cart cart);

    @Select("select * from tbl_cart where user_id = #{userId}")
    List<Cart> getCartsByUserId(String userId);

    @Delete("DELETE FROM tbl_cart WHERE id = #{id}")
    void deleteItem(String id);

    @Delete("DELETE FROM tbl_cart WHERE id in ${ids}")
    void deleteItems(String ids);

    @Update("update tbl_cart set shop_item_num = #{shopItemNum},update_date = #{updateDate} where id = #{id}")
    void updateCart(Cart cart);

    @Select("select * from tbl_cart where user_id = #{userId} and shop_item_id = #{shopItemId}")
    Cart getCartByUserIdAndShopItemId(Cart cart);
}
