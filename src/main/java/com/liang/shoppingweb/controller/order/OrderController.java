package com.liang.shoppingweb.controller.order;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.service.order.OrderService;
import com.liang.shoppingweb.service.order.OrderVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderVoService orderVoService;

    @GetMapping("/orderPage")
    public String getOrderPage() {
        return "order/orderPage";
    }

    @GetMapping("/orderDetail/{orderId}")
    public String getOrderDetail(@PathVariable("orderId") Integer orderId , Model model) {
        model.addAttribute("orderId",orderId);
        return "order/orderDetail";
    }

    @PostMapping("/createOrder")
    @ResponseBody
    public MyResponse createOrder(@RequestBody Map map) {
        MyResponse myResponse;
        try {
            Integer[] itemIds = ((ArrayList<?>) map.get("itemIds")).toArray(new Integer[0]);
            Integer receiveInfoId = (Integer) map.get("receiveInfoId");
            Order order = orderService.createOrder(itemIds,receiveInfoId);
            myResponse = MyResponse.getSuccessResponse("创建订单成功", order);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @GetMapping("/getUnFinishOrders")
    @ResponseBody
    public MyResponse getUnFinishOrders(){
        MyResponse myResponse;
        try {
            List<OrderVo> unFinishOrders = orderVoService.getUnFinishOrderVoByUsername();
            myResponse = MyResponse.getSuccessResponse("获取订单列表成功", unFinishOrders);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @GetMapping("/getOrderVoById/{orderId}")
    @ResponseBody
    public MyResponse getOrderVoById(@PathVariable("orderId") Integer orderId){
        MyResponse myResponse;
        try {
            OrderVo orderDetail = orderVoService.getOrderVoById(orderId);
            myResponse = MyResponse.getSuccessResponse("获取订单详细信息成功", orderDetail);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

}
