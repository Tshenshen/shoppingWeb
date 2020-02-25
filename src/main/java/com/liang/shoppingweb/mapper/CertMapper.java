package com.liang.shoppingweb.mapper;

import com.liang.shoppingweb.entity.cart.Cert;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CertMapper {

    @Insert("insert into tbl_cert(username,goods_id,goods_num,create_date) values(#{username},#{goodsId},#{goodsNum},#{createDate})")
    void addGoods(Cert cert);

    @Select("select * from tbl_cert where username = #{username}")
    List<Cert> getCertsByUsername(String username);

    @Delete("DELETE FROM tbl_cert WHERE id = #{id}")
    void deleteItem(Integer id);

    @Delete("DELETE FROM tbl_cert WHERE id in ${ids}")
    void deleteItems(String ids);

    @Update("update tbl_cert set goods_num = #{goodsNum},update_date = #{updateDate} where id = #{id}")
    void updateCert(Cert cert);

    @Select("select * from tbl_cert where username = #{username} and goods_id = #{goodsId}")
    Cert getCertByUsernameAndGoodsId(Cert cert);
}
