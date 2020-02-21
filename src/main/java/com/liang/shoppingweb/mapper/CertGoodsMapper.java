package com.liang.shoppingweb.mapper;

import com.liang.shoppingweb.entity.cart.CertVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CertGoodsMapper {

    List<CertVo> getCertWithGoodsInfoByUsername(String username);

    List<CertVo> getCertWithGoodsInfoByIds(String ids);

}
