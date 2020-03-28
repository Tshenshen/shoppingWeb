package com.liang.shoppingweb.controller.enterprise;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.entity.shop.Shop;
import com.liang.shoppingweb.entity.shop.ShopItem;
import com.liang.shoppingweb.entity.shop.ShopVo;
import com.liang.shoppingweb.service.enterprise.EnterpriseService;
import com.liang.shoppingweb.service.shop.ShopItemService;
import com.liang.shoppingweb.service.shop.ShopService;
import com.liang.shoppingweb.service.shop.ShopVoService;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopVoService shopVoService;
    @Autowired
    private ShopItemService shopItemService;

    @GetMapping("getTotalOrderPage")
    public String getTotalOrderPage(Model model) {
        model.addAttribute("type", "enterprise");
        return "order/totalOrderPage";
    }

    @PutMapping("rechargeToWalletFromUser")
    @ResponseBody
    public MyResponse rechargeToWalletFromUser(@RequestBody Enterprise enterprise) {
        MyResponse myResponse;
        try {
            enterprise = enterpriseService.rechargeToWalletFromUser(enterprise.getBalance());
            myResponse = MyResponse.getSuccessResponse("余额充值成功！", enterprise);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }

    @PutMapping("drawbackFromWalletToUser")
    @ResponseBody
    public MyResponse drawbackFromWalletToUser(@RequestBody Enterprise enterprise) {
        MyResponse myResponse;
        try {
            enterprise = enterpriseService.drawbackFromWalletToUser(enterprise.getBalance());
            myResponse = MyResponse.getSuccessResponse("余额提现成功！", enterprise);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse(e.getMessage());
        }
        return myResponse;
    }


    @PostMapping("addNewShopItem")
    @ResponseBody
    public MyResponse addNewShopItem(@RequestBody ShopItem shopItem) {
        MyResponse myResponse;
        try {
            shopItemService.addNewShopItem(shopItem);
            myResponse = MyResponse.getSuccessResponse("添加新商品成功！", shopItem);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("添加新商品失败！");
        }
        return myResponse;
    }

    @PutMapping("updateShopItem")
    @ResponseBody
    public MyResponse updateShopItem(@RequestBody ShopItem shopItem) {
        MyResponse myResponse;
        try {
            shopItemService.updateShopItem(shopItem);
            myResponse = MyResponse.getSuccessResponse("更新商品成功！", shopItem);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("更新商品失败！");
        }
        return myResponse;
    }

    @DeleteMapping("deleteShopItem")
    @ResponseBody
    public MyResponse deleteShopItem(@RequestParam String shopItemId) {
        MyResponse myResponse;
        try {
            shopItemService.deleteShopItem(shopItemId);
            myResponse = MyResponse.getSuccessResponse("删除商品成功！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("删除商品失败！");
        }
        return myResponse;
    }


    @GetMapping("getShopSettingPage")
    public String getShopSettingPage(@RequestParam String shopId, Model model) {
        model.addAttribute("shopId", shopId);
        return "shop/shopSettingPage";
    }

    @GetMapping("getShopVo/{shopId}")
    @ResponseBody
    public MyResponse getShopVoById(@PathVariable String shopId) {
        MyResponse myResponse;
        try {
            ShopVo shopVo = shopVoService.getShopVoById(shopId);
            myResponse = MyResponse.getSuccessResponse("获取店铺信息成功！", shopVo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取店铺信息失败！");
        }
        return myResponse;
    }


    @PutMapping("/updateShopInfo")
    @ResponseBody
    public MyResponse updateShopInfo(@RequestBody Shop shop) {
        MyResponse myResponse;
        try {
            shopService.updateShopInfoById(shop);
            myResponse = MyResponse.getSuccessResponse("修改店铺信息成功！", shop);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("修改店铺信息失败！");
        }
        return myResponse;
    }


    @DeleteMapping("/deleteShopImage/{shopId}")
    @ResponseBody
    public MyResponse deleteShopImage(@PathVariable String shopId, @RequestParam String imageName) {
        MyResponse myResponse;
        try {
            String images = shopService.deleteShopImage(shopId, imageName);
            myResponse = MyResponse.getSuccessResponse("删除图片成功！", images);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("删除图片失败！");
        }
        return myResponse;
    }

    @PostMapping("/uploadShopImage/{shopId}")
    @ResponseBody
    public MyResponse uploadShopImage(@PathVariable String shopId, MultipartFile file) {
        MyResponse myResponse;
        try {
            String images = shopService.uploadShopImage(shopId, file);
            myResponse = MyResponse.getSuccessResponse("上传图片成功！", images);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("上传图片失败！");
        }
        return myResponse;
    }

    @PostMapping("/createNewShop")
    @ResponseBody
    public MyResponse createNewShop(@RequestBody Shop shop) {
        MyResponse myResponse;
        try {
            shopService.createNewShop(shop);
            myResponse = MyResponse.getSuccessResponse("新建店铺成功！", shop);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("新建店铺失败！");
        }
        return myResponse;
    }

    @PutMapping("/updateShopEnable")
    @ResponseBody
    public MyResponse updateShopEnable(@RequestBody Shop shop) {
        MyResponse myResponse;
        try {
            shopService.updateShopEnable(shop);
            if ('1' == shop.getEnable()) {
                myResponse = MyResponse.getSuccessResponse("店铺已开启！", shop);
            } else {
                myResponse = MyResponse.getSuccessResponse("店铺已关闭！", shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("设置店铺开启或关闭失败！");
        }
        return myResponse;
    }

    @DeleteMapping("/deleteShopById/{id}")
    @ResponseBody
    public MyResponse deleteShopById(@PathVariable String id) {
        MyResponse myResponse;
        try {
            shopService.deleteShopById(id);
            myResponse = MyResponse.getSuccessResponse("删除店铺成功！");
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("删除店铺失败！");
        }
        return myResponse;
    }


    @GetMapping("/shopListPage")
    public String getShopListPage() {
        return "enterprise/shopListPage";
    }

    @GetMapping("/getMyShopList")
    @ResponseBody
    public MyResponse getMyShopList() {
        MyResponse myResponse;
        try {
            List<Shop> shopList = shopService.getShopListByEnterpriseId();
            myResponse = MyResponse.getSuccessResponse("获取店铺列表成功！", shopList);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取店铺列表失败！");
        }
        return myResponse;
    }

    @GetMapping("/center")
    public String getEnterprisePage(Model model) {
        Enterprise enterprise = enterpriseService.getEnterpriseByUserId(LoginUtils.getCurrentUserId());
        model.addAttribute("enterpriseInfo", enterprise);
        return "enterprise/center";
    }

}
