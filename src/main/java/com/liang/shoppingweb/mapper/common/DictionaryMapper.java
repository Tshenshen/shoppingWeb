package com.liang.shoppingweb.mapper.common;

import com.liang.shoppingweb.entity.common.Dictionary;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictionaryMapper {

    @Insert("insert into tbl_dic_type(id, name, value, parent_id, `order`, create_date) values(#{id}, #{name}, #{value}, #{parentId}, #{order}, #{createDate})")
    void addDictionaryToType(Dictionary dictionary);

    @Insert("insert into tbl_dic_style(id, name, value, parent_id, `order`, create_date) values(#{id}, #{name}, #{value}, #{parentId}, #{order}, #{createDate})")
    void addDictionaryToStyle(Dictionary dictionary);

    @Select("select * from tbl_dic_type order by `order` asc")
    List<Dictionary> getAllType();

    @Select("select * from tbl_dic_style where parent_id = #{parentId} order by `order` asc")
    List<Dictionary> getAllStyleByParentId(String parentId);

    @Insert("<script>" +
            "insert into tbl_dic_type(id, name, value, parent_id, `order`, create_date) values" +
            "<foreach collection=\"list\" item=\"dictionary\" index=\"index\"  separator=\",\">" +
            "(#{dictionary.id}, #{dictionary.name}, #{dictionary.value}, #{dictionary.parentId}, #{dictionary.order}, #{dictionary.createDate})" +
            "</foreach>" +
            "</script>")
    void batchAddDictionaryToType(List<Dictionary> dictionaries);

    @Insert("<script>" +
            "insert into tbl_dic_style(id, name, value, `order`, parent_id, create_date) values" +
            "<foreach collection=\"list\" item=\"dictionary\" index=\"index\"  separator=\",\">" +
            "(#{dictionary.id}, #{dictionary.name}, #{dictionary.value}, #{dictionary.order}, #{dictionary.parentId}, #{dictionary.createDate})" +
            "</foreach>" +
            "</script>")
    void batchAddDictionaryToStyle(List<Dictionary> dictionaries);

    @Select("select * from tbl_dic_style where parent_id = (select id from tbl_dic_type where value = #{value}) order by `order` asc")
    List<Dictionary> getAllStyleByParentValue(String value);
}
