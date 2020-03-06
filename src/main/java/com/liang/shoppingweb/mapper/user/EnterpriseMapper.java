package com.liang.shoppingweb.mapper.user;

import com.liang.shoppingweb.entity.user.Enterprise;
import org.apache.ibatis.annotations.Insert;

public interface EnterpriseMapper {

    @Insert("insert into tbl_enterprise(id, user_id, enterprise_name, phone_number, create_date)" +
            "values(#{id}, #{userId}, #{enterpriseName}, #{phoneNumber}, #{createDate})")
    void addEnterprise(Enterprise enterprise);
}
