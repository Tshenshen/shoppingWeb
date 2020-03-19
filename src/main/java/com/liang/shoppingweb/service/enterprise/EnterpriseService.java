package com.liang.shoppingweb.service.enterprise;

import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.mapper.enterprise.EnterpriseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class EnterpriseService {

    @Resource
    private EnterpriseMapper enterpriseMapper;

    public Enterprise getEnterpriseByUserId(String userId) {
        return enterpriseMapper.getEnterpriseByUserId(userId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateBalanceFromOrderVoList(List<OrderVo> orderVoList) {
        for (OrderVo orderVo : orderVoList) {
            balanceAddFromOrder(orderVo);
        }
    }

    private void balanceAddFromOrder(Order order) {
        order.setUpdateDate(new Date());
        enterpriseMapper.balanceAddFromOrder(order);
    }

    public void balanceMinusFromOrder(Order order) throws Exception {
        Enterprise enterprise = enterpriseMapper.getEnterpriseById(order.getEnterpriseId());
        if (enterprise.getBalance() - order.getSumPrice() < 0){
            throw new Exception("余额不足！！");
        }
        order.setUpdateDate(new Date());
        enterpriseMapper.balanceMinusFromOrder(order);
    }

}
