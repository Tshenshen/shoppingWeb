package com.liang.shoppingweb.mapper;

import com.liang.shoppingweb.entity.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from tbl_user")
    List<User> getAll();

    @Insert("insert into tbl_user(username,password,email,role,enable,create_date) values(#{username},#{password},#{email},#{role},#{enable},#{createDate})")
    void insertUser(User user);
}
