package com.liang.shoppingweb.mapper.order;

import com.liang.shoppingweb.entity.order.OrderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderWithCellMapper {

    List<OrderVo> getUnFinishOrderVoByUserId(String userId);

    OrderVo getOrderVoById(String id);
}
