package com.liang.shoppingweb.mapper.user;

import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from tbl_user")
    List<User> getAll();

    @Insert("insert into tbl_user(id,username,password,balance,email,role,enable,create_date,enterprise_id) values(#{id},#{username},#{password},#{balance},#{email},#{role},#{enable},#{createDate},#{enterpriseId})")
    void insertUser(User user);

    @Update("update tbl_user set last_login_date = #{lastLoginDate} where username = #{username}")
    void updateLastLoginDateByUsername(User user);

    @Select("select id,username,email,balance,role,enterprise_id,sex from tbl_user where username = #{username}")
    User getUserByName(String username);

    @Select("select id,username,email,balance,role,enterprise_id,sex from tbl_user where id = #{id}")
    User getUserById(String id);

    @Update("update tbl_user set balance = #{balance}, update_date = #{updateDate} where id = #{id}")
    void updateBalance(User user);

    @Update("update tbl_user set role = #{role}, update_date = #{updateDate}, enterprise_id = #{enterpriseId} where id = #{id}")
    void enterpriseRegister(User user);

    @Update("update tbl_user set balance = balance + #{sumPrice}, update_date = #{updateDate} where id = #{userId}")
    void balanceAddFromOrder(Order order);

    @Update("update tbl_user set balance = balance - #{sumPrice}, update_date = #{updateDate} where id = #{userId}")
    void balanceMinusFromOrder(Order order);

    @Insert("<script>insert into tbl_user(id,username,password,balance,email,role,enable,enterprise_id,create_date) values" +
            "<foreach collection=\"list\" item=\"user\"  separator=\",\">" +
            "(#{user.id},#{user.username},#{user.password},#{user.balance},#{user.email},#{user.role},#{user.enable},#{user.enterpriseId},#{user.createDate})"+
            "</foreach>" +
            "</script>")
    void batchInsertUser(List<User> userList);
}
