package com.liang.shoppingweb.mapper.order;

import com.liang.shoppingweb.entity.order.OrderCell;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderCellMapper {


    @Insert("<script>" +
            "INSERT INTO tbl_order_cell(order_id, id, shop_item_id, shop_item_num) VALUES" +
            "<foreach collection=\"param2\" item=\"item\" index=\"index\"  separator=\",\">" +
            "(#{param1},#{item.id},#{item.shopItemId},#{item.shopItemNum})" +
            "</foreach>" +
            "</script>")
    void insertOrderCells(String orderId,List<OrderCell> orderCells);
}
