package com.liang.shoppingweb.utils;

import com.liang.shoppingweb.entity.common.Tag;
import org.springframework.util.StringUtils;

import java.util.List;

public class QueryPramFormatUtils {

    public static String arrayToIn(Object[] list) {
        if (list == null || list.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("(");
        for (Object item : list) {
            sb.append("'").append(item).append("'").append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    public static String listToIn(List list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("(");
        for (Object item : list) {
            sb.append("'").append(item).append("'").append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    public static String strToIn(String str, String Separator) {
        if (!StringUtils.isEmpty(str)) {
            return arrayToIn(str.split(Separator));
        }
        return null;
    }

    public static String tagListToTagListQueryString(List<Tag> tagList, String alias, String column) {
        if (tagList == null || tagList.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("INNER JOIN (SELECT t1.shop_id FROM  ");
        sb.append("( SELECT shop_id FROM tbl_tag WHERE dic_id = \"").append(tagList.get(0).getDicId()).append("\" ) t1 ");
        for (int i = 2; i <= tagList.size(); i++) {
            sb.append("INNER JOIN ");
            sb.append("( SELECT shop_id FROM tbl_tag WHERE dic_id = \"").append(tagList.get(i - 1).getDicId()).append("\" ) t").append(i);
            sb.append(" ON t").append(i - 1).append(".shop_id = t").append(i).append(".shop_id");
        }

        sb.append(") t ON t.shop_id = ").append(alias).append(".").append(column);
        return sb.toString();
    }
}
