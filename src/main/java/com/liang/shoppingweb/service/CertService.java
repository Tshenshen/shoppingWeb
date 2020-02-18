package com.liang.shoppingweb.service;

import com.github.pagehelper.PageHelper;
import com.liang.shoppingweb.entity.cart.Cert;
import com.liang.shoppingweb.entity.cart.CertVo;
import com.liang.shoppingweb.mapper.CertGoodsMapper;
import com.liang.shoppingweb.mapper.CertMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CertService {

    @Resource
    private CertMapper certMapper;
    @Resource
    private CertGoodsMapper certGoodsMapper;

    public void addGoods(Cert cert){
        cert.setCreateDate(new Date());
        certMapper.addGoods(cert);
    }

    public List<Cert> getCertsByUsername(String username){
        return certMapper.getCertsByUsername(username);
    }

    public List<CertVo> getCertWithGoodsInfoByUsername(String username){
//        PageHelper.startPage()
        List<CertVo> certs = certGoodsMapper.getCertWithGoodsInfoByUsername(username);
        return certs;
    }
}
