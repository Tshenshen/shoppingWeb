package com.liang.shoppingweb.mapper.enterprise;

import com.liang.shoppingweb.entity.enterprise.Enterprise;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EnterpriseMapper {

    @Insert("insert into tbl_enterprise(id, user_id, enterprise_name, phone_number, create_date)" +
            "values(#{id}, #{userId}, #{enterpriseName}, #{phoneNumber}, #{createDate})")
    void addEnterprise(Enterprise enterprise);

    @Select("select * from tbl_enterprise where user_id = #{userId}")
    Enterprise getEnterpriseByUserId(String userId);

    @Select("select * from tbl_enterprise where id = #{id}")
    Enterprise getEnterpriseById(String id);

    @Update("update tbl_enterprise set balance = #{balance}, update_date = #{updateDate} where id = #{id}")
    void updateBalance(Enterprise enterprise);
}
