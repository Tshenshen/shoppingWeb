package com.liang.shoppingweb.service;

import com.liang.shoppingweb.entity.cart.Cert;
import com.liang.shoppingweb.mapper.CertMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CertService {

    @Resource
    private CertMapper certMapper;

    public void addGoods(Cert cert){
        cert.setCreateDate(new Date());
        certMapper.addGoods(cert);
    }

    public List<Cert> getCertsByUsername(String username){
        return certMapper.getCertsByUsername(username);
    }
}
