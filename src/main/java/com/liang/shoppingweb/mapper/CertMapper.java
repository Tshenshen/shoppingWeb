package com.liang.shoppingweb.mapper;

import com.liang.shoppingweb.entity.cart.Cert;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CertMapper {

    @Insert("insert into tbl_cert(username,goods_id,goods_num,create_date) values(#{username},#{goodsId},#{goodsNum},#{createDate})")
    void addGoods(Cert cert);

}
