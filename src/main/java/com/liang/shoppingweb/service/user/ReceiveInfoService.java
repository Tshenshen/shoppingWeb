package com.liang.shoppingweb.service.user;

import com.liang.shoppingweb.entity.user.ReceiveInfo;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.user.ReceiveInfoMapper;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ReceiveInfoService {

    @Resource
    private ReceiveInfoMapper receiveInfoMapper;

    public ReceiveInfo addNewReceiver(ReceiveInfo receiveInfo) {
        User user = LoginUtils.getCurrentUser();
        receiveInfo.setUsername(user.getUsername());
        receiveInfo.setCreateDate(new Date());
        receiveInfoMapper.addNewReceiver(receiveInfo);
        return receiveInfo;
    }


    public void deleteReceiver(int id){
        receiveInfoMapper.deleteReceiver(id);
    }

    public List<ReceiveInfo> getReceiversByUsername(){
        User user = LoginUtils.getCurrentUser();
        return receiveInfoMapper.getReceiversByUsername(user.getUsername());
    }

    public ReceiveInfo updateReceiver(ReceiveInfo receiveInfo){
        receiveInfo.setUpdateDate(new Date());
        receiveInfoMapper.updateReceiver(receiveInfo);
        return receiveInfo;
    }

}
