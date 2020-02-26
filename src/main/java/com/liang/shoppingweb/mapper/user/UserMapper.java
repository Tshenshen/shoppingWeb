package com.liang.shoppingweb.mapper.user;

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

    @Insert("insert into tbl_user(username,password,balance,email,role,enable,create_date) values(#{username},#{password},0,#{email},#{role},#{enable},#{createDate})")
    void insertUser(User user);

    @Update("update tbl_user set last_login_date = #{lastLoginDate} where username = #{username}")
    void updateLastLoginDateByUsername(User user);

    @Select("select id,username,email,balance,role,sex from tbl_user where username = #{username}")
    User getUserByName(String username);
}
