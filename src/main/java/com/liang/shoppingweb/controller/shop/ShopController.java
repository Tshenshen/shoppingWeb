package com.liang.shoppingweb.controller.shop;

import com.github.pagehelper.PageInfo;
import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.common.PageConstant;
import com.liang.shoppingweb.entity.shop.Shop;
import com.liang.shoppingweb.entity.shop.ShopVo;
import com.liang.shoppingweb.service.shop.ShopService;
import com.liang.shoppingweb.service.user.CollectService;
import com.liang.shoppingweb.utils.JSONUtil;
import com.liang.shoppingweb.utils.SearchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

@Controller
@RequestMapping("shop")
public class ShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private CollectService collectService;


    @GetMapping("getCollectByShopId")
    public String getCollectByShopId(String shopId) {
        return "forward:/user/getCollectByShopId";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/list")
    @ResponseBody
    public MyResponse indexByPageNumber(@RequestParam int pageNum) {
        MyResponse myResponse;
        try {
            PageInfo<Shop> pageInfo = shopService.getShopListByPage(pageNum, PageConstant.pageSize);
            myResponse = MyResponse.getSuccessResponse("获取店铺列表成功！", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取店铺列表失败！");
        }
        return myResponse;
    }

    @GetMapping("/getShopListBySearchInfo")
    @ResponseBody
    public MyResponse getShopListBySearchInfo(@RequestParam String shopSearchInfoStr) {
        MyResponse myResponse;
        try {
            shopSearchInfoStr = StringUtils.uriDecode(shopSearchInfoStr, Charset.forName("utf-8"));
            SearchInfo searchInfo = JSONUtil.json2Object(shopSearchInfoStr, SearchInfo.class);
            PageInfo<Shop> pageInfo = shopService.getShopListBySearchInfo(searchInfo);
            myResponse = MyResponse.getSuccessResponse("获取店铺列表成功！", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取店铺列表失败！");
        }
        return myResponse;
    }

    @GetMapping("/getRecommendPage")
    public String getRecommendPage() {
        return "shop/recommendPage";
    }

    @GetMapping("/getRecommendShopList")
    @ResponseBody
    public MyResponse getRecommendShopList(@RequestParam int pageNum, HttpServletRequest request) {
        MyResponse myResponse;
        try {
            PageInfo<Shop> pageInfo = shopService.getRecommendShopList(pageNum, request);
            myResponse = MyResponse.getSuccessResponse("获取推荐店铺列表成功！", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取推荐店铺列表失败！");
        }
        return myResponse;
    }

    @GetMapping("detailPage")
    public String detailPage(@RequestParam String shopId, Model model) {
        model.addAttribute("shopId", shopId);
        return "shop/detailPage";
    }

    @GetMapping("getShopVo/{id}")
    @ResponseBody
    public MyResponse getShopVo(@PathVariable String id) {
        MyResponse myResponse;
        try {
            ShopVo shopVo = shopService.getShopVoById(id);
            shopVo.setCollect(collectService.getCollectByShopId(id));
            myResponse = MyResponse.getSuccessResponse("获取店铺详细信息成功！", shopVo);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取店铺详细信息失败！");
        }
        return myResponse;
    }
}
