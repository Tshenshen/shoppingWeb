package com.liang.shoppingweb.service.enterprise;

import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.mapper.enterprise.EnterpriseMapper;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class EnterpriseService {

    @Resource
    private EnterpriseMapper enterpriseMapper;
    @Resource
    private UserService userService;

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
        if (enterprise.getBalance() - order.getSumPrice() < 0) {
            throw new Exception("余额不足！！");
        }
        order.setUpdateDate(new Date());
        enterpriseMapper.balanceMinusFromOrder(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public Enterprise rechargeToWalletFromUser(double balance) throws Exception {
//        从用户钱包扣除金额
        userService.drawbackFromWallet(balance);
//        为商家钱包充值金额
        Enterprise enterprise = enterpriseMapper.getEnterpriseByUserId(LoginUtils.getCurrentUserId());
        enterprise.setBalance(enterprise.getBalance() + balance);
        enterprise.setUpdateDate(new Date());
        enterpriseMapper.updateBalance(enterprise);
        return enterprise;
    }

    @Transactional(rollbackFor = Exception.class)
    public Enterprise drawbackFromWalletToUser(double balance) throws Exception {
        Enterprise enterprise = enterpriseMapper.getEnterpriseByUserId(LoginUtils.getCurrentUserId());
        double newBalance = enterprise.getBalance() - balance;
        if (newBalance < 0) {
            throw new Exception("余额不足！！");
        }
        enterprise.setBalance(newBalance);
        enterprise.setUpdateDate(new Date());
        enterpriseMapper.updateBalance(enterprise);
        userService.rechargeToWallet(balance);
        return enterprise;
    }
}
