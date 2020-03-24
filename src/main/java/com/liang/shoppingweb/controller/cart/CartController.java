package com.liang.shoppingweb.controller.cart;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.cart.Cart;
import com.liang.shoppingweb.entity.cart.CartItemVo;
import com.liang.shoppingweb.entity.cart.CartShopVo;
import com.liang.shoppingweb.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addGoods")
    @ResponseBody
    public MyResponse addGoods(@RequestBody Cart cart) {
        MyResponse myResponse;
        try {
            cartService.addGoods(cart);
            myResponse = MyResponse.getSuccessResponse("物品添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @PostMapping("/buySingleGoods")
    @ResponseBody
    public MyResponse buySingleGoods(@RequestBody Cart cart) {
        MyResponse myResponse;
        try {
            cart = cartService.buySingleGoods(cart);
            myResponse = MyResponse.getSuccessResponse("物品添加成功", cart.getId());
        } catch (Exception e) {
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
            List<CartShopVo> cartShopVoList = cartService.getCartShopVoListByUserId();
            myResponse = MyResponse.getSuccessResponse("获取购物车列表成功", cartShopVoList);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取购物车列表失败!");
        }
        return myResponse;
    }

    @PostMapping("/cartsListByIds")
    @ResponseBody
    public MyResponse getCartsByIds(@RequestBody HashMap map) {
        MyResponse myResponse;
        try {
            String ids = (String) map.get("ids");
            List<CartItemVo> cartItemVos = cartService.getCartWithGoodsInfoByIds(ids);
            myResponse = MyResponse.getSuccessResponse("获取购物车列表成功", cartItemVos);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取购物车列表失败!");
        }
        return myResponse;
    }

    @DeleteMapping("/deleteItem/{id}")
    @ResponseBody
    public MyResponse deleteCartItem(@PathVariable("id") String id) {
        MyResponse myResponse;
        try {
            cartService.deleteCartItem(id);
            List<CartShopVo> cartShopVoList = cartService.getCartShopVoListByUserId();
            myResponse = MyResponse.getSuccessResponse("删除物品成功！", cartShopVoList);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("删除物品失败！");
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
            List<CartShopVo> cartShopVoList = cartService.getCartShopVoListByUserId();
            myResponse = MyResponse.getSuccessResponse("删除选中物品成功！", cartShopVoList);
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
