package com.liang.shoppingweb.mapper;

import com.liang.shoppingweb.entity.order.OrderVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderWithCellMapper {

    List<OrderVo> getUnFinishOrderVoByUsername(String username);

}
