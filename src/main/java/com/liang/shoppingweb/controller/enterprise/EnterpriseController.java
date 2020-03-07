package com.liang.shoppingweb.controller.enterprise;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.service.enterprise.EnterpriseService;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseService enterpriseService;


    @GetMapping("/shopListPage")
    public String getShopListPage(Model model) {

        return "enterprise/shopListPage";
    }

    @GetMapping("/center")
    public String getEnterprisePage(Model model) {
        if (!LoginUtils.isEnterprise()) {
            return "enterprise/register";
        }
        Enterprise enterprise = enterpriseService.getEnterpriseByUserId(LoginUtils.getCurrentUserId());
        model.addAttribute("enterpriseInfo", enterprise);
        return "enterprise/center";
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
}
