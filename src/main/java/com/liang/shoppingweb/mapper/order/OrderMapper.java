package com.liang.shoppingweb.mapper.order;

import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("insert into tbl_order(id, user_id, sum_price, receive_info_id, create_date, state) " +
            "values(#{id}, #{userId}, #{sumPrice}, #{receiveInfoId}, #{createDate}, '1') ")
    void insertOrder(Order order);

    @Select("select * from tbl_order where id = #{id}")
    Order getOrderById(String id);

    @Update("update tbl_order set state = #{state}, update_date = #{updateDate} where id = #{id}")
    void updateOrderState(Order order);

    @Delete("delete from tbl_order where id = #{id}")
    void deleteById(String orderId);

    @Insert("<script>" +
            "insert into tbl_order(id, user_id, shop_id, enterprise_id, sum_price, receive_info_id, create_date, state) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\">" +
            "(#{item.id},#{item.userId},#{item.shopId},#{item.enterpriseId},#{item.sumPrice},#{item.receiveInfoId},#{item.createDate},#{item.state})" +
            "</foreach>" +
            "</script>")
    void addOrders(List<OrderVo> orderVoList);
}
