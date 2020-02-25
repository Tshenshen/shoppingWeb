package com.liang.shoppingweb.controller;

import com.liang.shoppingweb.common.AuthorityConstant;
import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.cart.Cert;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.service.CertService;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Controller
public class CertController {

    @Autowired
    private CertService certService;

    @PostMapping("/cert/addGoods")
    @ResponseBody
    public MyResponse addGoods(@RequestBody Cert cert) {
        MyResponse myResponse;
        User user = LoginUtils.getCurrentUser();
        if (user == null) {
            return MyResponse.getFailedResponse("用户未登录");
        }
        cert.setUsername(user.getUsername());
        try {
            certService.addGoods(cert);
            myResponse = MyResponse.getSuccessResponse("物品添加成功");
        }catch (Exception e){
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @GetMapping("/cert/certPage")
    public String certPage() {
        return "cert/certPage";
    }

    @GetMapping("/cert/CertsList")
    @ResponseBody
    public MyResponse getCertsByUsername(HttpSession session) {
        MyResponse myResponse = new MyResponse();
        User user = (User) session.getAttribute(AuthorityConstant.session_user_key);
        if (user != null) {
            myResponse.setSuccess(true);
            myResponse.setContent(certService.getCertWithGoodsInfoByUsername(user.getUsername()));
            myResponse.setMessage("获取购物车列表成功");
        } else {
            myResponse.setSuccess(false);
            myResponse.setMessage("获取购物车列表失败（用户未登录）！！！");
        }
        return myResponse;
    }

    @DeleteMapping("/cert/deleteItem/{id}")
    @ResponseBody
    public MyResponse deleteCertItem(@PathVariable("id") Integer id) {
        MyResponse myResponse = new MyResponse();
        try {
            certService.deleteCertItem(id);
            myResponse.setSuccess(true);
        } catch (Exception e) {
            myResponse.setSuccess(false);
            myResponse.setMessage("删除物品失败");
        }
        return myResponse;
    }

    @DeleteMapping("/cert/deleteItems")
    @ResponseBody
    public MyResponse deleteCertItem(@RequestBody Map map) {
        MyResponse myResponse;
        try {
            Integer[] itemIds = ((ArrayList<?>) map.get("itemIds")).toArray(new Integer[0]);
            certService.deleteCertItems(itemIds);
            myResponse = MyResponse.getSuccessResponse("删除选中物品成功！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("删除选中物品失败！");
        }
        return myResponse;
    }

    @PutMapping("/cert/updateItemNum")
    @ResponseBody
    public MyResponse updateNum(@RequestBody Cert cert) {
        MyResponse myResponse;
        try {
            cert.setUpdateDate(new Date());
            certService.updateItemNum(cert);
            myResponse = MyResponse.getSuccessResponse("更新购物车物品数量成功！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("更新购物车物品数量失败！");
        }
        return myResponse;
    }
}
