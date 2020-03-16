package com.liang.shoppingweb.service.order;

import com.liang.shoppingweb.entity.cart.CartVo;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderCell;
import com.liang.shoppingweb.entity.shop.ShopItem;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.cart.CartVoMapper;
import com.liang.shoppingweb.mapper.cart.CartMapper;
import com.liang.shoppingweb.mapper.order.OrderCellMapper;
import com.liang.shoppingweb.mapper.order.OrderMapper;
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


    public Order getOrderById(String id) {
        return orderMapper.getOrderById(id);
    }

    /**
     * 创建订单
     */
    @Transactional
    public Order createOrder(String[] ids, String receiverInfoId) throws Exception {

        String in_ids = QueryPramFormatUtils.strToIn(ids);
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
        orderMapper.insertOrder(order);
        //插入子订单
        orderCellMapper.insertOrderCells(order.getId(), orderCells);
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public void payWithWallet(String orderId) throws Exception {
        Order order;
        try {
            order = orderMapper.getOrderById(orderId);
            order.setState(2);
            order.setUpdateDate(new Date());
            orderMapper.updateOrderState(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("支付失败！！");
        }
        userService.payWithWallet(order.getSumPrice());
    }
}
