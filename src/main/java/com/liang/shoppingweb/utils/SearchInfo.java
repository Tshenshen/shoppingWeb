package com.liang.shoppingweb.utils;

import com.liang.shoppingweb.entity.common.Tag;
import lombok.Data;

import java.util.List;

@Data
public class SearchInfo {
    private String type;
    private String style;
    private String keyword;
    private int pageNum;
    private List<Tag> tagList;
    private String tagListQueryString;

    public void tagListToTagListQueryString() {
        this.tagListQueryString = QueryPramFormatUtils.tagListToTagListQueryString(this.tagList, "s", "id");
        System.out.println(this.tagListQueryString);
    }
}
