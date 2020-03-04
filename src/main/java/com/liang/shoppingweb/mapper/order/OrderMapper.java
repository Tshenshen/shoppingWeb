package com.liang.shoppingweb.mapper.order;

import com.liang.shoppingweb.entity.order.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    @Insert("insert into tbl_order(id, user_id, sum_price, receive_info_id, create_date, state) " +
            "values(#{id}, #{userId}, #{sumPrice}, #{receiveInfoId}, #{createDate}, '1') ")
    void insertOrder(Order order);

    @Select("select * from tbl_order where id = #{id}")
    Order getOrderById(String id);

    @Update("update tbl_order set state = #{state}, update_date = #{updateDate} where id = #{id}")
    void updateOrderState(Order order);
}
