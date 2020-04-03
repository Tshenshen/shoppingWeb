package com.liang.shoppingweb.mapper.enterprise;

import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.entity.order.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface EnterpriseMapper {

    @Insert("insert into tbl_enterprise(id, user_id, enterprise_name, phone_number, balance, create_date)" +
            "values(#{id}, #{userId}, #{enterpriseName}, #{phoneNumber}, #{balance}, #{createDate})")
    void addEnterprise(Enterprise enterprise);

    @Select("select * from tbl_enterprise where user_id = #{userId}")
    Enterprise getEnterpriseByUserId(String userId);

    @Select("select * from tbl_enterprise where id = #{id}")
    Enterprise getEnterpriseById(String id);

    @Update("update tbl_enterprise set balance = #{balance}, update_date = #{updateDate} where id = #{id}")
    void updateBalance(Enterprise enterprise);

    @Update("update tbl_enterprise set balance = balance + #{sumPrice}, update_date = #{updateDate} where id = #{enterpriseId}")
    void balanceAddFromOrder(Order order);

    @Update("update tbl_enterprise set balance = balance - #{sumPrice}, update_date = #{updateDate} where id = #{enterpriseId}")
    void balanceMinusFromOrder(Order order);

    @Insert("<script>insert into tbl_enterprise(id, user_id, enterprise_name, phone_number, balance, create_date) values" +
            "<foreach collection=\"list\" item=\"enterprise\" index=\"index\"  separator=\",\">" +
            "(#{enterprise.id}, #{enterprise.userId}, #{enterprise.enterpriseName}, #{enterprise.phoneNumber}, #{enterprise.balance}, #{enterprise.createDate})" +
            "</foreach>" +
            "</script>")
    void batchAddEnterprise(List<Enterprise> enterpriseList);
}
