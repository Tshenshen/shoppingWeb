package com.liang.shoppingweb.mapper.user;

import com.liang.shoppingweb.entity.user.ReceiveInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReceiveInfoMapper {

    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @Insert("insert into tbl_receive_info(username, receiver, phone_number, address, create_date) " +
            "values(#{username}, #{receiver}, #{phoneNumber}, #{address}, #{createDate})")
    void addNewReceiver(ReceiveInfo receiveInfo);

    @Delete("delete from tbl_receive_info where id = #{id}")
    void deleteReceiver(int id);

    @Select("select * from tbl_receive_info where username = #{username}")
    List<ReceiveInfo> getReceiversByUsername(String username);

    @Update("update tbl_receive_info " +
            "set receiver = #{receiver}, address = #{address}, phone_number = #{phoneNumber}, update_date = #{updateDate}" +
            "where id = #{id}")
    void updateReceiver(ReceiveInfo receiveInfo);
}
