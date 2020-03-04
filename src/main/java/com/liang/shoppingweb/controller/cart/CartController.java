package com.liang.shoppingweb.controller.cart;

import com.liang.shoppingweb.common.AuthorityConstant;
import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.cart.Cart;
import com.liang.shoppingweb.entity.cart.CartVo;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.service.cart.CartService;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addGoods")
    @ResponseBody
    public MyResponse addGoods(@RequestBody Cart cart) {
        MyResponse myResponse;
        User user = LoginUtils.getCurrentUser();
        if (user == null) {
            return MyResponse.getFailedResponse("用户未登录");
        }
        cart.setUserId(user.getId());
        try {
            cartService.addGoods(cart);
            myResponse = MyResponse.getSuccessResponse("物品添加成功");
        }catch (Exception e){
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @PostMapping("/buySingleGoods")
    @ResponseBody
    public MyResponse buySingleGoods(@RequestBody Cart cart) {
        MyResponse myResponse;
        cart.setUserId(LoginUtils.getCurrentUserId());
        try {
            CartVo cartVo = cartService.buySingleGoods(cart);
            myResponse = MyResponse.getSuccessResponse("物品添加成功", cartVo);
        }catch (Exception e){
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @GetMapping("/cartPage")
    public String cartPage() {
        return "cart/cartPage";
    }

    @GetMapping("/cartsList")
    @ResponseBody
    public MyResponse getCartsByUserId() {
        MyResponse myResponse;
        try {
            List<CartVo> cartVoList = cartService.getCartWithGoodsInfoByUserId();
            myResponse = MyResponse.getSuccessResponse("获取购物车列表成功",cartVoList);
        } catch (Exception e){
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取购物车列表失败!");
        }
        return myResponse;
    }

    @DeleteMapping("/deleteItem/{id}")
    @ResponseBody
    public MyResponse deleteCartItem(@PathVariable("id") Integer id) {
        MyResponse myResponse = new MyResponse();
        try {
            cartService.deleteCartItem(id);
            myResponse.setSuccess(true);
        } catch (Exception e) {
            myResponse.setSuccess(false);
            myResponse.setMessage("删除物品失败");
        }
        return myResponse;
    }

    @DeleteMapping("/deleteItems")
    @ResponseBody
    public MyResponse deleteCartItem(@RequestBody Map map) {
        MyResponse myResponse;
        try {
            String[] itemIds = ((ArrayList<?>) map.get("itemIds")).toArray(new String[0]);
            cartService.deleteCartItems(itemIds);
            myResponse = MyResponse.getSuccessResponse("删除选中物品成功！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("删除选中物品失败！");
        }
        return myResponse;
    }

    @PutMapping("/updateItemNum")
    @ResponseBody
    public MyResponse updateNum(@RequestBody Cart cart) {
        MyResponse myResponse;
        try {
            cart.setUpdateDate(new Date());
            cartService.updateItemNum(cart);
            myResponse = MyResponse.getSuccessResponse("更新购物车物品数量成功！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("更新购物车物品数量失败！");
        }
        return myResponse;
    }
}
