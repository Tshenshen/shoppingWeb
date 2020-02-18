package com.liang.shoppingweb.entity.cart;

import com.liang.shoppingweb.entity.goods.Goods;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class CertVo extends Cert {

    /**
     * 商品信息（根据GoodId获取）
     */
    private Goods goods ;
}
