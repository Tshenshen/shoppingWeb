package com.liang.shoppingweb.controller.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.common.PageConstant;
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


    @GetMapping("getOrderVoListByOrderInfo")
    @ResponseBody
    public MyResponse getOrderVoListByOrderInfo(@RequestParam String type, @RequestParam int state, @RequestParam int pageNum) {
        MyResponse myResponse;
        try {
            Order order = new Order();
            order.setState(state);
            if ("user".equals(type)) {
                order.setUserId(LoginUtils.getCurrentUserId());
            } else if ("enterprise".equals(type)) {
                order.setEnterpriseId(LoginUtils.getCurrentUserEnterpriseId());
            }
            PageInfo<OrderVo> pageInfo = orderService.getOrderVoListByOrderInfoAndPageNum(order,pageNum);
            myResponse = MyResponse.getSuccessResponse("获取订单列表成功", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取订单列表失败");
        }
        return myResponse;
    }

    @PutMapping("/changeReceiver")
    @ResponseBody
    public MyResponse changeReceiver(@RequestBody Order order) {
        MyResponse myResponse;
        try {
            orderService.changeReceiver(order);
            myResponse = MyResponse.getSuccessResponse("修改收件地址成功");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("修改收件地址失败");
        }
        return myResponse;
    }


    @PutMapping("/refundApply")
    @ResponseBody
    public MyResponse refundApply(@RequestBody Order order) {
        MyResponse myResponse;
        try {
            orderService.refundApply(order);
            OrderVo orderVo = orderVoService.getOrderVoById(order.getId());
            myResponse = MyResponse.getSuccessResponse("订单申请退款成功", orderVo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("订单申请退款失败");
        }
        return myResponse;
    }

    @PutMapping("/sendById/{orderId}")
    @ResponseBody
    public MyResponse sendById(@PathVariable String orderId) {
        MyResponse myResponse;
        try {
            orderService.orderSend(orderId);
            OrderVo orderVo = orderVoService.getOrderVoById(orderId);
            myResponse = MyResponse.getSuccessResponse("确认发货成功", orderVo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("确认发货失败");
        }
        return myResponse;
    }

    @PutMapping("/refundApply/{orderId}")
    @ResponseBody
    public MyResponse refundAccept(@PathVariable String orderId) {
        MyResponse myResponse;
        try {
            orderService.refundAccept(orderId);
            myResponse = MyResponse.getSuccessResponse("订单退款接受成功");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @PutMapping("/refundRefuse/{orderId}")
    @ResponseBody
    public MyResponse refundRefuse(@PathVariable String orderId) {
        MyResponse myResponse;
        try {
            orderService.refundRefuse(orderId);
            myResponse = MyResponse.getSuccessResponse("订单退款拒绝成功");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @PutMapping("/receiveById/{orderId}")
    @ResponseBody
    public MyResponse receiveById(@PathVariable String orderId) {
        MyResponse myResponse;
        try {
            orderService.orderReceive(orderId);
            myResponse = MyResponse.getSuccessResponse("确认收货成功");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("确认收货失败");
        }
        return myResponse;
    }

    @DeleteMapping("/cancelById/{orderId}")
    @ResponseBody
    public MyResponse cancelById(@PathVariable String orderId) {
        MyResponse myResponse;
        try {
            orderService.orderCancel(orderId);
            myResponse = MyResponse.getSuccessResponse("取消订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("取消订单失败");
        }
        return myResponse;
    }

    @PostMapping("/orderPage")
    public String getOrderPage(String ids, Model model) {
        model.addAttribute("ids", ids);
        return "order/orderPage";
    }

    @GetMapping("/payPage")
    public String getPayPage(String orderId, String SW_USER_TOKEN, Model model) {
        if (!LoginUtils.isSameUser(SW_USER_TOKEN)) {
            return "redirect:/";
        }
        Order order = orderService.getOrderById(orderId);
        if (!order.getUserId().equals(LoginUtils.getCurrentUserId())){
            return "error/403";
        }
        model.addAttribute("order", order);
        User user = userService.getUserByName(LoginUtils.getCurrentUsername());
        model.addAttribute("user", user);
        return "order/payPage";
    }

    @GetMapping("/orderDetail/{orderId}")
    public String getOrderDetail(@PathVariable("orderId") String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        Order order = orderService.getOrderById(orderId);
        if (!order.getUserId().equals(LoginUtils.getCurrentUserId())){
            return "error/403";
        }
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

    @GetMapping("/getUnFinishOrdersByEnterpriseId")
    @ResponseBody
    public MyResponse getUnFinishOrdersByEnterpriseId() {
        MyResponse myResponse;
        try {
            List<OrderVo> unFinishOrders = orderVoService.getUnFinishOrdersByEnterpriseId();
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
        if (!LoginUtils.isSameUser(SW_USER_TOKEN)) {
            return "redirect:/";
        }
        try {
            orderService.payWithWallet(orderId);
            myResponse = MyResponse.getSuccessResponse("支付成功", "订单支付成功！！感谢您的购买！！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("支付失败", e.getMessage());
        }
        model.addAttribute("payResult", myResponse);
        return "order/payResult";
    }

}
