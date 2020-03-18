package com.liang.shoppingweb.service.order;

import com.liang.shoppingweb.common.OrderStateConstant;
import com.liang.shoppingweb.entity.cart.CartVo;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderCell;
import com.liang.shoppingweb.entity.order.OrderCellVo;
import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.entity.shop.ShopItem;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.cart.CartMapper;
import com.liang.shoppingweb.mapper.cart.CartVoMapper;
import com.liang.shoppingweb.mapper.order.OrderCellMapper;
import com.liang.shoppingweb.mapper.order.OrderMapper;
import com.liang.shoppingweb.mapper.order.OrderWithCellMapper;
import com.liang.shoppingweb.service.enterprise.EnterpriseService;
import com.liang.shoppingweb.service.shop.ShopItemService;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.LoginUtils;
import com.liang.shoppingweb.utils.QueryPramFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Resource
    private CartVoMapper cartVoMapper;
    @Resource
    private ShopItemService shopItemService;
    @Resource
    private CartMapper cartMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderCellMapper orderCellMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Resource
    private OrderWithCellMapper orderWithCellMapper;


    public Order getOrderById(String id) {
        return orderMapper.getOrderById(id);
    }

    /**
     * 创建订单
     */
    @Transactional
    public Order createOrder(String[] ids, String receiverInfoId) throws Exception {

        String in_ids = QueryPramFormatUtils.arrayToIn(ids);
        List<CartVo> cartVoList = cartVoMapper.getCartWithGoodsInfoByIds(in_ids);
        Order order;
        order = createOrder(cartVoList, receiverInfoId);
        //删除购物车
        cartMapper.deleteItems(in_ids);
        return order;
    }

    /**
     * 创建订单(单件物品）
     */
    @Transactional
    public Order createSingleOrder(String shopItemId, int shopItemNum, String receiverInfoId) throws Exception {
        List<CartVo> cartVoList = new ArrayList<>();
        CartVo cartVo = new CartVo();
        cartVo.setShopItemNum(shopItemNum);
        cartVo.setShopItemId(shopItemId);
        cartVo.setShopItem(shopItemService.getShopItemById(shopItemId));
        cartVoList.add(cartVo);
        return createOrder(cartVoList, receiverInfoId);
    }


    @Transactional
    public Order createOrder(List<CartVo> cartVoList, String receiverInfoId) throws Exception {
        User currentUser = LoginUtils.getCurrentUser();
        if (currentUser == null) {
            throw new Exception("用户未登录！！");
        }
        List<OrderCell> orderCells = new ArrayList<>();
        double sumPrice = 0.0;
        OrderCell orderCell;

        //更新库存,生成子订单
        for (CartVo cartVo : cartVoList) {
            orderCell = cartVo.convertToOrderCell();
            orderCell.setId(UUID.randomUUID().toString());
            orderCells.add(orderCell);
            ShopItem shopItem = cartVo.getShopItem();
            int newStock = shopItem.getStock() - cartVo.getShopItemNum();
            if (newStock < 0) {
                throw new Exception(cartVo.getShopItem().getName() + " 库存不足！！！");
            }
            shopItem.setStock(newStock);
            shopItem.setUpdateDate(new Date());
            shopItemService.updateShopItemStock(shopItem);
            sumPrice = sumPrice + cartVo.getShopItemNum() * shopItem.getPrice();
        }

        //创建总订单
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCreateDate(new Date());
        order.setUserId(currentUser.getId());
        order.setReceiveInfoId(receiverInfoId);
        order.setSumPrice(sumPrice);
        order.setState(OrderStateConstant.unPay);
        orderMapper.insertOrder(order);
        //插入子订单
        orderCellMapper.insertOrderCells(order.getId(), orderCells);
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public void payWithWallet(String orderId) throws Exception {
        OrderVo orderVo;
        String shopId = "";
        List<OrderVo> orderVoList = new ArrayList<>();
        OrderVo newOrderVo = new OrderVo();
        orderVo = orderWithCellMapper.getOrderVoById(orderId);
        userService.payWithWallet(orderVo.getSumPrice());
        try {
            //拆分订单
            for (OrderCellVo orderCellVo : orderVo.getOrderCells()) {
                if (!shopId.equals(orderCellVo.getShopVo().getId())) {
                    shopId = orderCellVo.getShopVo().getId();
                    newOrderVo = new OrderVo();
                    newOrderVo.setId(UUID.randomUUID().toString());
                    newOrderVo.setUserId(orderVo.getUserId());
                    newOrderVo.setShopId(shopId);
                    newOrderVo.setEnterpriseId(orderCellVo.getShopVo().getEnterpriseId());
                    newOrderVo.setReceiveInfoId(orderVo.getReceiveInfoId());
                    newOrderVo.setState(OrderStateConstant.unSend);
                    newOrderVo.setSumPrice(0.0);
                    newOrderVo.setOrderCells(new ArrayList<>());
                    newOrderVo.setCreateDate(new Date());
                    orderVoList.add(newOrderVo);
                }
                orderCellVo.setOrderId(newOrderVo.getId());
                orderCellMapper.updateOrderId(orderCellVo);
                newOrderVo.getOrderCells().add(orderCellVo);
                newOrderVo.setSumPrice(newOrderVo.getSumPrice() + orderCellVo.getShopItemNum() * orderCellVo.getShopVo().getShopItems().get(0).getPrice());
            }
            //删除旧的订单
            orderMapper.deleteById(orderId);
            //插入新的订单
            orderMapper.addOrders(orderVoList);
            enterpriseService.updateBalanceFromOrderVoList(orderVoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("支付失败！！订单更新失败！！");
        }
    }

    public void orderCancel(String orderId) {
        updateStateById(orderId,OrderStateConstant.cancel);
    }

    public void orderSend(String orderId) {
        updateStateById(orderId,OrderStateConstant.unReceive);
    }

    public void orderReceive(String orderId) {
        updateStateById(orderId,OrderStateConstant.finish);
    }

    private void updateStateById(String orderId, int state) {
        Order order = new Order();
        order.setId(orderId);
        order.setState(state);
        order.setUpdateDate(new Date());
        orderMapper.updateOrderStateById(order);
    }

    public void refundApply(Order order) {
        order.setUpdateDate(new Date());
        order.setState(OrderStateConstant.refund);
        orderMapper.refundApply(order);
    }

    public void refundAccept(String orderId) {
        updateStateById(orderId,OrderStateConstant.refundAccept);
    }

    public void refundRefuse(String orderId) {
        updateStateById(orderId,OrderStateConstant.refundRefuse);
    }
}
