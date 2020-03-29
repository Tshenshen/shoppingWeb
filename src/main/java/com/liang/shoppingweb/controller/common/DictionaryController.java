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

    @GetMapping("/getDictionaryListByRootValue")
    @ResponseBody
    public MyResponse getDictionaryListByRootValue(String value) {
        MyResponse myResponse;
        try {
            List<Dictionary> allType = dictionaryService.getDictionaryListByRootValue(value);
            myResponse = MyResponse.getSuccessResponse("获取字典列表成功！", allType);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取字典列表失败！");
        }
        return myResponse;
    }


    @GetMapping("/getDictionaryListByParentId")
    @ResponseBody
    public MyResponse getDictionaryListByParentId(String parentId) {
        MyResponse myResponse;
        try {
            List<Dictionary> dictionaryList = dictionaryService.getDictionaryListByParentId(parentId);
            myResponse = MyResponse.getSuccessResponse("获取字典列表成功！", dictionaryList);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取字典列表失败！");
        }
        return myResponse;
    }

    @GetMapping("getTagDictionaryListByStyleIdAndKeyWord")
    @ResponseBody
    public MyResponse getTagDicListByStyleIdAndKeyWord(String styleId, String keyWord) {
        MyResponse myResponse;
        try {
            List<Dictionary> TagDicList = dictionaryService.getTagDicListByStyleIdAndKeyWord(styleId, keyWord);
            myResponse = MyResponse.getSuccessResponse("获取标签列表成功！", TagDicList);
        } catch (Exception e) {
            e.printStackTrace();
            myResponse = MyResponse.getFailedResponse("获取标签列表失败！");
        }
        return myResponse;
    }

}
