package com.liang.shoppingweb.service.user;

import com.liang.shoppingweb.entity.user.ReceiveInfo;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.user.ReceiveInfoMapper;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReceiveInfoService {

    @Resource
    private ReceiveInfoMapper receiveInfoMapper;

    public ReceiveInfo addNewReceiver(ReceiveInfo receiveInfo) {
        User user = LoginUtils.getCurrentUser();
        receiveInfo.setId(UUID.randomUUID().toString());
        receiveInfo.setUserId(user.getId());
        receiveInfo.setCreateDate(new Date());
        receiveInfoMapper.addNewReceiver(receiveInfo);
        return receiveInfo;
    }


    public void deleteReceiver(String id){
        receiveInfoMapper.deleteReceiver(id);
    }

    public List<ReceiveInfo> getReceiversByUserId(){
        return receiveInfoMapper.getReceiversByUserId(LoginUtils.getCurrentUserId());
    }

    public ReceiveInfo updateReceiver(ReceiveInfo receiveInfo){
        receiveInfo.setUpdateDate(new Date());
        receiveInfoMapper.updateReceiver(receiveInfo);
        return receiveInfo;
    }

}
