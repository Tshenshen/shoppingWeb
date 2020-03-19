package com.liang.shoppingweb.service.order;

import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.order.OrderWithCellMapper;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderVoService {

    @Resource
    private OrderWithCellMapper orderWithCellMapper;

    public List<OrderVo> getUnFinishOrderVoByUserId() {
        return orderWithCellMapper.getUnFinishOrderVoByUserId(LoginUtils.getCurrentUserId());
    }

    public OrderVo getOrderVoById(String id) {
        return orderWithCellMapper.getOrderVoById(id);
    }

    public List<OrderVo> getUnFinishOrdersByEnterpriseId() {
        return orderWithCellMapper.getUnFinishOrdersByEnterpriseId(LoginUtils.getCurrentUserEnterpriseId());
    }
}
