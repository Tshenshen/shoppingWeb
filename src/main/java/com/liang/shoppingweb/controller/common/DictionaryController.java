package com.liang.shoppingweb.controller.common;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.common.Dictionary;
import com.liang.shoppingweb.service.common.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/getAllType")
    @ResponseBody
    public MyResponse getAllType() {
        MyResponse myResponse;
        try {
            List<Dictionary> allType = dictionaryService.getAllType();
            myResponse = MyResponse.getSuccessResponse("获取种类成功！", allType);
        } catch (Exception e) {
            myResponse = MyResponse.getFailedResponse("获取种类失败！");
        }
        return myResponse;
    }

    @GetMapping("/getAllStyleByParentId")
    @ResponseBody
    public MyResponse getAllStyleByParentId(String parentId) {
        MyResponse myResponse;
        try {
            List<Dictionary> allStyle = dictionaryService.getAllStyleByParentId(parentId);
            myResponse = MyResponse.getSuccessResponse("获取类型成功！", allStyle);
        } catch (Exception e) {
            myResponse = MyResponse.getFailedResponse("获取类型失败！");
        }
        return myResponse;
    }

    @GetMapping("/getAllStyleByParentValue")
    @ResponseBody
    public MyResponse getAllStyleByParentValue(String value) {
        MyResponse myResponse;
        try {
            List<Dictionary> allStyle = dictionaryService.getAllStyleByParentValue(value);
            myResponse = MyResponse.getSuccessResponse("获取类型成功！", allStyle);
        } catch (Exception e) {
            myResponse = MyResponse.getFailedResponse("获取类型失败！");
        }
        return myResponse;
    }

}
