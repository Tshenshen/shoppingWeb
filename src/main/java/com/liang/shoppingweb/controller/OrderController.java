package com.liang.shoppingweb.controller;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/orderPage")
    public String getOrderPage() {
        return "order/orderPage";
    }

    @PostMapping("/order/createOrder")
    @ResponseBody
    public MyResponse createOrder(@RequestBody Map map) {
        MyResponse myResponse;
        try {
            Integer[] itemIds = ((ArrayList<?>) map.get("itemIds")).toArray(new Integer[0]);
            Order order = orderService.createOrder(itemIds);
            myResponse = MyResponse.getSuccessResponse("创建订单成功", order);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

}
