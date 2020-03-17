package com.liang.shoppingweb.service.enterprise;

import com.liang.shoppingweb.entity.enterprise.Enterprise;
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


    @Transactional
    public void updateBalanceFromOrderVoList(List<OrderVo> orderVoList)  {
        for (OrderVo orderVo : orderVoList) {
            Enterprise enterprise = enterpriseMapper.getEnterpriseById(orderVo.getEnterpriseId());
            double newBalance = enterprise.getBalance() + orderVo.getSumPrice();
            enterprise.setUpdateDate(new Date());
            enterprise.setBalance(newBalance);
            enterpriseMapper.updateBalance(enterprise);
        }
    }


}
