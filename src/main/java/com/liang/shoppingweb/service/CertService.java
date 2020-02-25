package com.liang.shoppingweb.service;

import com.liang.shoppingweb.entity.cart.Cert;
import com.liang.shoppingweb.entity.cart.CertVo;
import com.liang.shoppingweb.entity.goods.Goods;
import com.liang.shoppingweb.mapper.CertGoodsMapper;
import com.liang.shoppingweb.mapper.CertMapper;
import com.liang.shoppingweb.mapper.GoodsMapper;
import com.liang.shoppingweb.utils.QueryPramFormatUtils;
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
    @Resource
    private GoodsMapper goodsMapper;

    public void addGoods(Cert newCert) throws Exception {
        //根据用户名和goodsId获取cert记录
        Cert cert = certMapper.getCertByUsernameAndGoodsId(newCert);
        //没有就插入新纪录
        if (cert == null) {
            newCert.setCreateDate(new Date());
            certMapper.addGoods(newCert);
            return;
        }
        //如果有就更新数量
        Goods goods = goodsMapper.getGoodsById(cert.getGoodsId());
        int newGoodsNum = cert.getGoodsNum()+ newCert.getGoodsNum();
        if (newGoodsNum > goods.getStock()){
            throw new Exception("库存不足");
        }
        cert.setUpdateDate(new Date());
        cert.setGoodsNum(newGoodsNum);
        certMapper.updateCert(cert);
    }

    public List<Cert> getCertsByUsername(String username) {
        return certMapper.getCertsByUsername(username);
    }

    public List<CertVo> getCertWithGoodsInfoByUsername(String username) {
//        PageHelper.startPage()
        List<CertVo> certs = certGoodsMapper.getCertWithGoodsInfoByUsername(username);
        return certs;
    }

    public void deleteCertItem(Integer id) {
        certMapper.deleteItem(id);
    }

    public void deleteCertItems(Integer[] itemIds) {
        String ids = QueryPramFormatUtils.toIn(itemIds);
        certMapper.deleteItems(ids);
    }

    public void updateItemNum(Cert cert) {
        certMapper.updateCert(cert);
    }
}
