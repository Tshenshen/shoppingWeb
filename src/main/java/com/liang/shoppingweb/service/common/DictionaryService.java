package com.liang.shoppingweb.service.common;

import com.liang.shoppingweb.common.DictionaryType;
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

    public void addDictionary(Dictionary dictionary,int dictionaryType) {
        dictionary.setId(UUID.randomUUID().toString());
        dictionary.setCreateDate(new Date());
        if (dictionaryType == DictionaryType.type){
            dictionaryMapper.addDictionaryToType(dictionary);
        }else if (dictionaryType == DictionaryType.style){
            dictionaryMapper.addDictionaryToStyle(dictionary);
        }
    }

    public List<Dictionary> getAllType(){
        return dictionaryMapper.getAllType();
    }


    public void batchAddDictionary(List<Dictionary> dictionaries,int dictionaryType){
        if (dictionaryType == DictionaryType.type){
            dictionaryMapper.batchAddDictionaryToType(dictionaries);
        }else if (dictionaryType == DictionaryType.style){
            dictionaryMapper.batchAddDictionaryToStyle(dictionaries);
        }
    }


    public List<Dictionary> getAllStyleByParentId(String parentId) {
        return dictionaryMapper.getAllStyleByParentId(parentId);
    }

    public List<Dictionary> getAllStyleByParentValue(String value) {
        return dictionaryMapper.getAllStyleByParentValue(value);
    }
}
