package com.liang.shoppingweb.mapper;

import com.liang.shoppingweb.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from tbl_user")
    List<User> getAll();
}
