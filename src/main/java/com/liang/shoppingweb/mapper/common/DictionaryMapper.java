package com.liang.shoppingweb.mapper.common;

import com.liang.shoppingweb.entity.common.Dictionary;
import com.liang.shoppingweb.entity.common.DictionaryVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictionaryMapper {
    @Insert("<script>" +
            "insert into tbl_dictionary set " +
            "id = #{id}, " +
            "name = #{name}, " +
            "value = #{value}, " +
            "<if test=\"parentId != null and '' != parentId\">" +
            "parent_id = #{parentId}, " +
            "</if>" +
            "`order` = #{order}, " +
            "create_date = #{createDate}" +
            "</script>")
    void addDictionary(Dictionary dictionary);

    @Select("select * from tbl_dictionary where parent_id is null order by `order` asc")
    List<Dictionary> getRootDictionaryList();

    @Select("select * from tbl_dictionary " +
            "where parent_id = (select id from tbl_dictionary where parent_id is null and value = #{value}) " +
            "order by `order` asc")
    List<Dictionary> getDictionaryListByRootValue(String value);

    @Select("select * from tbl_dictionary where parent_id = #{parentId} order by `order` asc")
    List<Dictionary> getDictionaryListByParentId(String parentId);

    @Select("SELECT\n" +
            "t.*\n" +
            "FROM\n" +
            "tbl_dictionary AS d\n" +
            "INNER JOIN tbl_dictionary AS c ON d.id = #{arg0} AND d.id = c.parent_id\n" +
            "INNER JOIN tbl_dictionary AS t ON c.id = t.parent_id\n" +
            "where t.name like concat(\"%\",#{arg1},\"%\") ")
    List<Dictionary> getTagDictionaryListByStyleIdAndKeyWord(String styleId, String keyWord);

    @Insert("<script>" +
            "insert into tbl_dictionary(id, name, value, parent_id, `order`, create_date) values" +
            "<foreach collection=\"list\" item=\"dictionary\" index=\"index\"  separator=\",\">" +
            "(#{dictionary.id}, #{dictionary.name}, #{dictionary.value}, #{dictionary.parentId}, #{dictionary.order}, #{dictionary.createDate})" +
            "</foreach>" +
            "</script>")
    void batchAddDictionary(List<Dictionary> dictionaryList);

    DictionaryVo getRootDictionaryVoByValue(String value);

    DictionaryVo getDictionaryVoByParentId(String parentId);
}
