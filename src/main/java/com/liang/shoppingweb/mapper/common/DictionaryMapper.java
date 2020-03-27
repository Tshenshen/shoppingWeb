package com.liang.shoppingweb.mapper.common;

import com.liang.shoppingweb.entity.common.Dictionary;
import com.liang.shoppingweb.entity.common.DictionaryVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictionaryMapper {
//
//    @Insert("insert into tbl_dic_type(id, name, value, parent_id, `order`, create_date) values(#{id}, #{name}, #{value}, #{parentId}, #{order}, #{createDate})")
//    void addDictionaryToType(Dictionary dictionary);
//
//    @Insert("insert into tbl_dic_style(id, name, value, parent_id, `order`, create_date) values(#{id}, #{name}, #{value}, #{parentId}, #{order}, #{createDate})")
//    void addDictionaryToStyle(Dictionary dictionary);
//
//    @Select("select * from tbl_dic_type order by `order` asc")
//    List<Dictionary> getAllType();
//
//    @Select("select * from tbl_dic_style where parent_id = #{parentId} order by `order` asc")
//    List<Dictionary> getAllStyleByParentId(String parentId);
//
//    @Insert("<script>" +
//            "insert into tbl_dic_type(id, name, value, parent_id, `order`, create_date) values" +
//            "<foreach collection=\"list\" item=\"dictionary\" index=\"index\"  separator=\",\">" +
//            "(#{dictionary.id}, #{dictionary.name}, #{dictionary.value}, #{dictionary.parentId}, #{dictionary.order}, #{dictionary.createDate})" +
//            "</foreach>" +
//            "</script>")
//    void batchAddDictionaryToType(List<Dictionary> dictionaries);
//
//    @Insert("<script>" +
//            "insert into tbl_dic_style(id, name, value, `order`, parent_id, create_date) values" +
//            "<foreach collection=\"list\" item=\"dictionary\" index=\"index\"  separator=\",\">" +
//            "(#{dictionary.id}, #{dictionary.name}, #{dictionary.value}, #{dictionary.order}, #{dictionary.parentId}, #{dictionary.createDate})" +
//            "</foreach>" +
//            "</script>")
//    void batchAddDictionaryToStyle(List<Dictionary> dictionaries);
//
//    @Select("select * from tbl_dic_style where parent_id = (select id from tbl_dic_type where value = #{value}) order by `order` asc")
//    List<Dictionary> getAllStyleByParentValue(String value);

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
