package com.liang.shoppingweb.mapper.order;

import com.liang.shoppingweb.entity.order.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface OrderMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("insert into tbl_order(username, sum_price, receive_info_id, create_date, state) " +
            "values(#{username},#{sumPrice},#{receiveInfoId},#{createDate},'1') ")
    void insertOrder(Order order);
}
