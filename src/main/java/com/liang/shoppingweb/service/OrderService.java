package com.liang.shoppingweb.service;

import com.liang.shoppingweb.entity.cart.CertVo;
import com.liang.shoppingweb.entity.goods.Goods;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderCell;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.*;
import com.liang.shoppingweb.utils.LoginUtils;
import com.liang.shoppingweb.utils.QueryPramFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private CertGoodsMapper certGoodsMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private CertMapper certMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderCellMapper orderCellMapper;


    /**
     * 创建订单
     */
    @Transactional
    public Order createOrder(Integer[] ids) throws Exception {
        User currentUser = LoginUtils.getCurrentUser();
        if (currentUser == null) {
            throw new Exception("用户未登录！！");
        }
        String in_ids = QueryPramFormatUtils.toIn(ids);
        List<CertVo> certVoList = certGoodsMapper.getCertWithGoodsInfoByIds(in_ids);
        List<OrderCell> orderCells = new ArrayList<>();
        double sumPrice = 0.0;

        //更新库存,生成子订单
        for (CertVo certVo : certVoList) {
            orderCells.add(certVo.convertToOrderCell());
            Goods goods = certVo.getGoods();
            int newStock = goods.getStock() - certVo.getGoodsNum();
            goods.setStock(newStock);
            goods.setUpdateDate(new Date());
            goodsMapper.updateGoodsStock(goods);
            if (newStock < 0) {
                throw new Exception(certVo.getGoods().getName() + " 库存不足！！！");
            }
            sumPrice = sumPrice + certVo.getGoodsNum() * goods.getPrice();
        }
        //删除购物车
        certMapper.deleteItems(in_ids);

        //创建总订单
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setUsername(currentUser.getUsername());
//        order.setReceiveInfoId();todo 收件人
        order.setSumPrice(sumPrice);
        orderMapper.insertOrder(order);
        //插入子订单
        orderCellMapper.insertOrderCells(order.getId(),orderCells);
        return order;
    }

}