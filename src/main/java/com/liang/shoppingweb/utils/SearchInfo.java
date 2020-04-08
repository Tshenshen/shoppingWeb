package com.liang.shoppingweb.utils;

import com.liang.shoppingweb.entity.common.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchInfo {
    private String type;
    private String style;
    private String keyword;
    private int pageNum;
    private List<Tag> tagList;
    private int tagListSize;
    private String tagListQueryString;
    private String tagDicIds;

    public void tagListToTagListQueryString() {
        this.tagListQueryString = QueryPramFormatUtils.tagListToTagListQueryString(this.tagList, "s", "id");
        System.out.println(this.tagListQueryString);
    }

    public void tagListToTagDicIds(){
        if (tagList == null || tagList.size() == 0) {
            this.tagDicIds = null;
            return;
        }
        List<String> tagDicIds = new ArrayList<>();
        for (Tag tag : tagList){
            tagDicIds.add(tag.getDicId());
        }
        this.tagListSize = tagList.size();
        this.tagDicIds = QueryPramFormatUtils.listToIn(tagDicIds);
    }
}
