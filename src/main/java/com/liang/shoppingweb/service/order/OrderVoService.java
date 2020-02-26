package com.liang.shoppingweb.service.order;

import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.order.OrderWithCellMapper;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderVoService {

    @Resource
    private OrderWithCellMapper orderWithCellMapper;

    public List<OrderVo> getUnFinishOrderVoByUsername() throws Exception {
        User userInfo = LoginUtils.getCurrentUser();
        if (userInfo == null) {
            throw new Exception("用户未登录！！");
        }
        return orderWithCellMapper.getUnFinishOrderVoByUsername(userInfo.getUsername());
    }
}
