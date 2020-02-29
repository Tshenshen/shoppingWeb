package com.liang.shoppingweb.mapper.order;

import com.liang.shoppingweb.entity.order.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("insert into tbl_order(username, sum_price, receive_info_id, create_date, state) " +
            "values(#{username},#{sumPrice},#{receiveInfoId},#{createDate},'1') ")
    void insertOrder(Order order);

    @Select("select * from tbl_order where id = #{id}")
    Order getOrderById(int id);

    @Update("update tbl_order set state = #{state}, update_date = #{updateDate} where id = #{id}")
    void updateOrderState(Order order);
}
