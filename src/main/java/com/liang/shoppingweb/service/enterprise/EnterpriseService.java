package com.liang.shoppingweb.service.enterprise;

import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.mapper.enterprise.EnterpriseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EnterpriseService {

    @Resource
    private EnterpriseMapper enterpriseMapper;

    public Enterprise getEnterpriseByUserId(String userId) {
        return enterpriseMapper.getEnterpriseByUserId(userId);
    }
}
