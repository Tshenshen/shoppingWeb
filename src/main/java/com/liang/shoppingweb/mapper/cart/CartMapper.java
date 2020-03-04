package com.liang.shoppingweb.mapper.cart;

import com.liang.shoppingweb.entity.cart.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("insert into tbl_cart(id,user_id,goods_id,goods_num,create_date) values(#{id},#{userId},#{goodsId},#{goodsNum},#{createDate})")
    void addGoods(Cart cart);

    @Select("select * from tbl_cart where user_id = #{userId}")
    List<Cart> getCartsByUserId(String userId);

    @Delete("DELETE FROM tbl_cart WHERE id = #{id}")
    void deleteItem(Integer id);

    @Delete("DELETE FROM tbl_cart WHERE id in ${ids}")
    void deleteItems(String ids);

    @Update("update tbl_cart set goods_num = #{goodsNum},update_date = #{updateDate} where id = #{id}")
    void updateCart(Cart cart);

    @Select("select * from tbl_cart where user_id = #{userId} and goods_id = #{goodsId}")
    Cart getCartByUserIdAndGoodsId(Cart cart);
}
