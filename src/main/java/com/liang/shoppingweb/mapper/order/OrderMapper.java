package com.liang.shoppingweb.mapper.order;

import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("insert into tbl_order(id, user_id, sum_price, receive_info_id, create_date, state) " +
            "values(#{id}, #{userId}, #{sumPrice}, #{receiveInfoId}, #{createDate}, #{state}) ")
    void insertOrder(Order order);

    @Select("select * from tbl_order where id = #{id}")
    Order getOrderById(String id);

    @Update("update tbl_order set state = #{state}, update_date = #{updateDate} where id = #{id}")
    void updateOrderStateById(Order order);

    @Delete("delete from tbl_order where id = #{id}")
    void deleteById(String orderId);

    @Insert("<script>" +
            "insert into tbl_order(id, user_id, shop_id, enterprise_id, sum_price, receive_info_id, create_date, state) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\">" +
            "(#{item.id},#{item.userId},#{item.shopId},#{item.enterpriseId},#{item.sumPrice},#{item.receiveInfoId},#{item.createDate},#{item.state})" +
            "</foreach>" +
            "</script>")
    void addOrders(List<OrderVo> orderVoList);

    @Update("update tbl_order set state = #{state}, update_date = #{updateDate}, refund_reason = #{refundReason} where id = #{id}")
    void refundApply(Order order);

    @Update("update tbl_order set receive_info_id = #{receiveInfoId}, update_date = #{updateDate} where id = #{id}")
    void updateOrderReceiveInfoId(Order order);
}
