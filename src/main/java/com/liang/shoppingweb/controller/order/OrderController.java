package com.liang.shoppingweb.controller.order;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.service.order.OrderService;
import com.liang.shoppingweb.service.order.OrderVoService;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.LoginUtils;
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
    @Autowired
    private UserService userService;

    @GetMapping("/orderPage")
    public String getOrderPage() {
        return "order/orderPage";
    }

    @GetMapping("/payPage")
    public String getPayPage(String orderId, String SW_USER_TOKEN, Model model) {
        if(!LoginUtils.isSameUser(SW_USER_TOKEN)){
            return "redirect:/";
        }
        User user = userService.getUserByName(LoginUtils.getCurrentUsername());
        model.addAttribute("user",user);
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order",order);
        return "order/payPage";
    }

    @GetMapping("/orderDetail/{orderId}")
    public String getOrderDetail(@PathVariable("orderId") String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "order/orderDetail";
    }

    @PostMapping("/createOrder")
    @ResponseBody
    public MyResponse createOrder(@RequestBody Map map) {
        MyResponse myResponse;
        try {
            String[] itemIds = ((ArrayList<?>) map.get("itemIds")).toArray(new String[0]);
            String receiveInfoId = (String) map.get("receiveInfoId");
            Order order = orderService.createOrder(itemIds, receiveInfoId);
            myResponse = MyResponse.getSuccessResponse("创建订单成功", order);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @GetMapping("/getUnFinishOrders")
    @ResponseBody
    public MyResponse getUnFinishOrders() {
        MyResponse myResponse;
        try {
            List<OrderVo> unFinishOrders = orderVoService.getUnFinishOrderVoByUserId();
            myResponse = MyResponse.getSuccessResponse("获取订单列表成功", unFinishOrders);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @GetMapping("/getOrderVoById/{orderId}")
    @ResponseBody
    public MyResponse getOrderVoById(@PathVariable("orderId") String orderId) {
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

    @PostMapping("/payWithWallet")
    public String payWithWallet(String orderId, String SW_USER_TOKEN, Model model) {
        MyResponse myResponse;
        if(!LoginUtils.isSameUser(SW_USER_TOKEN)){
            return "redirect:/";
        }
        try {
            orderService.payWithWallet(orderId);
            myResponse = MyResponse.getSuccessResponse("支付成功","订单支付成功！！感谢您的购买！！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("支付失败",e.getMessage());
        }
        model.addAttribute("payResult",myResponse);
        return "order/payResult";
    }

}
