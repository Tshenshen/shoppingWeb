package com.liang.shoppingweb.controller.user;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.entity.user.Collect;
import com.liang.shoppingweb.entity.user.ReceiveInfo;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.service.user.CollectService;
import com.liang.shoppingweb.service.user.ReceiveInfoService;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ReceiveInfoService receiveInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private CollectService collectService;

    @GetMapping("getCollectByShopId")
    @ResponseBody
    public MyResponse getCollectByShopId(String shopId) {
        MyResponse myResponse;
        try {
            Collect collect = collectService.getCollectByShopId(shopId);
            myResponse = MyResponse.getSuccessResponse("获取收藏信息成功！", collect);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取收藏信息失败！");
        }
        return myResponse;
    }

    @PostMapping("collectShop")
    @ResponseBody
    public MyResponse collectShop(@RequestBody Collect collect) {
        MyResponse myResponse;
        try {
            collect = collectService.collectShop(collect);
            myResponse = MyResponse.getSuccessResponse("收藏店铺成功！", collect);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("收藏店铺失败！");
        }
        return myResponse;
    }

    @DeleteMapping("cancelCollectShop")
    @ResponseBody
    public MyResponse cancelCollectShop(@RequestBody Collect collect) {
        MyResponse myResponse;
        try {
            collectService.cancelCollectShop(collect);
            myResponse = MyResponse.getSuccessResponse("已取消收藏！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("取消收藏失败！");
        }
        return myResponse;
    }

    @GetMapping("getTotalOrderPage")
    public String getTotalOrderPage(Model model) {
        model.addAttribute("type", "user");
        return "order/totalOrderPage";
    }

    @GetMapping("/getReceiveSettingPage")
    public String getReceiveSettingPage() {
        return "user/receiveSetting";
    }

    @GetMapping("/getReceivers")
    @ResponseBody
    public MyResponse getReceives() {
        MyResponse myResponse;
        try {
            List<ReceiveInfo> receivers = receiveInfoService.getReceiversByUserId();
            myResponse = MyResponse.getSuccessResponse("获取收件地址成功！", receivers);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取收件地址失败！");
        }
        return myResponse;
    }

    @DeleteMapping("/deleteReceiver/{id}")
    @ResponseBody
    public MyResponse getReceives(@PathVariable("id") String id) {
        MyResponse myResponse;
        try {
            receiveInfoService.deleteReceiver(id);
            myResponse = MyResponse.getSuccessResponse("删除收件地址成功！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("删除收件地址失败！");
        }
        return myResponse;
    }

    @PutMapping("/updateReceiver")
    @ResponseBody
    public MyResponse updateReceiver(@RequestBody ReceiveInfo receiveInfo) {
        MyResponse myResponse;
        try {
            receiveInfo = receiveInfoService.updateReceiver(receiveInfo);
            myResponse = MyResponse.getSuccessResponse("更新收件地址成功！", receiveInfo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("更新收件地址失败！");
        }
        return myResponse;
    }

    @PostMapping("/addNewReceiver")
    @ResponseBody
    public MyResponse addNewReceiver(@RequestBody ReceiveInfo receiveInfo) {
        MyResponse myResponse;
        try {
            receiveInfo = receiveInfoService.addNewReceiver(receiveInfo);
            myResponse = MyResponse.getSuccessResponse("添加新收件地址成功！", receiveInfo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("添加新收件地址失败！");
        }
        return myResponse;
    }

    @GetMapping("/getUserCenterPage")
    public String getUserCenterPage(Model model) {
        User user = LoginUtils.getCurrentUser();
        User userInfo = userService.getUserByName(user.getUsername());
        model.addAttribute("userInfo", userInfo);
        return "user/center";
    }


    @GetMapping("/getCurrentUser")
    @ResponseBody
    public MyResponse getCurrentUser(HttpSession session) {
        MyResponse myResponse = new MyResponse();
        User user = (User) session.getAttribute("SW_USER");
        if (user == null) {
            myResponse.setSuccess(false);
            myResponse.setMessage("用户未登录");
        } else {
            myResponse.setSuccess(true);
            myResponse.setContent(user);
            myResponse.setMessage("获取当前用户成功");
            System.out.println(myResponse);
        }
        return myResponse;
    }


    @PostMapping("/enterpriseRegister")
    @ResponseBody
    public MyResponse enterpriseRegister(@RequestBody Enterprise enterprise) {
        MyResponse myResponse;
        try {
            userService.enterpriseRegister(enterprise);
            myResponse = MyResponse.getSuccessResponse("注册商家成功！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("注册商家失败！");
        }
        return myResponse;
    }

    @GetMapping("/toEnterpriseCenter")
    public String toEnterpriseCenter() {
        if (!LoginUtils.isEnterprise()) {
            return "user/enterpriseRegisterPage";
        }
        return "redirect:/enterprise/center";
    }


}
