package com.liang.shoppingweb.service.common;

import com.liang.shoppingweb.entity.common.Dictionary;
import com.liang.shoppingweb.mapper.common.DictionaryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    public void addDictionary(Dictionary dictionary) {
        dictionary.setId(UUID.randomUUID().toString());
        dictionary.setCreateDate(new Date());
        dictionaryMapper.addDictionary(dictionary);
    }

    public List<Dictionary> getTypeDictionaryList() {
        return dictionaryMapper.getDictionaryListByRootValue("TYPE_DIC");
    }


    public List<Dictionary> getDictionaryListByParentId(String parentId) {
        return dictionaryMapper.getDictionaryListByParentId(parentId);
    }

    public List<Dictionary> getDictionaryListByRootValue(String value) {
        return dictionaryMapper.getDictionaryListByRootValue(value);
    }

    public List<Dictionary> getTagDicListByStyleIdAndKeyWord(String styleId, String keyWord) {
        return dictionaryMapper.getTagDictionaryListByStyleIdAndKeyWord(styleId, keyWord);
    }
}
