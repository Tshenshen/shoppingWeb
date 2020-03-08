package com.liang.shoppingweb.mapper.enterprise;

import com.liang.shoppingweb.entity.enterprise.Enterprise;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface EnterpriseMapper {

    @Insert("insert into tbl_enterprise(id, user_id, enterprise_name, phone_number, create_date)" +
            "values(#{id}, #{userId}, #{enterpriseName}, #{phoneNumber}, #{createDate})")
    void addEnterprise(Enterprise enterprise);

    @Select("select * from tbl_enterprise where user_id = #{userId}")
    Enterprise getEnterpriseByUserId(String userId);


}
