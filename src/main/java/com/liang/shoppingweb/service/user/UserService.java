package com.liang.shoppingweb.service.user;

import com.liang.shoppingweb.common.AuthorityConstant;
import com.liang.shoppingweb.entity.enterprise.Enterprise;
import com.liang.shoppingweb.entity.order.Order;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.exception.MyException;
import com.liang.shoppingweb.mapper.enterprise.EnterpriseMapper;
import com.liang.shoppingweb.mapper.user.UserMapper;
import com.liang.shoppingweb.utils.EncodeUtils;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private EnterpriseMapper enterpriseMapper;


    public List<User> getAll() {
        return userMapper.getAll();
    }

    public void insertUser(User user) throws MyException {
        try {
            user.setPassword(EncodeUtils.encodeByBCrypt(user.getPassword()));
            user.setEnable('1');
            user.setCreateDate(new Date());
            user.setId(UUID.randomUUID().toString());
            userMapper.insertUser(user);
        } catch (Exception e) {
            MyException exception = new MyException(e);
            exception.addErrMsg("username", "用户名已存在");
            throw exception;
        }
    }

    public void updateLastLoginDateByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setLastLoginDate(new Date());
        System.out.println(new Date());
        userMapper.updateLastLoginDateByUsername(user);
    }

    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }


    public void balanceAddFromOrder(Order order) {
        order.setUpdateDate(new Date());
        userMapper.balanceAddFromOrder(order);
    }

    public void balanceMinusFromOrder(Order order) throws Exception {
        User user = userMapper.getUserById(order.getUserId());
        if (user.getBalance() - order.getSumPrice() < 0) {
            throw new Exception("余额不足！！");
        }
        order.setUpdateDate(new Date());
        userMapper.balanceMinusFromOrder(order);
    }


    @Transactional
    public void enterpriseRegister(Enterprise enterprise) {
        User user = new User();
        user.setId(LoginUtils.getCurrentUserId());

        //创建新商家
        String enterpriseId = UUID.randomUUID().toString();
        enterprise.setId(enterpriseId);
        enterprise.setUserId(user.getId());
        enterprise.setUpdateDate(new Date());
        enterpriseMapper.addEnterprise(enterprise);

        //更新数据库用户
        user.setRole(AuthorityConstant.shop);
        user.setEnterpriseId(enterpriseId);
        user.setUpdateDate(new Date());
        userMapper.enterpriseRegister(user);

        //更新本地用户
        user = userMapper.getUserById(user.getId());
        LoginUtils.setCurrentUser(user);
        // 得到当前的认证信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //  生成当前的所有授权
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
        // 添加 ROLE_VIP 授权
        updatedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        // 生成新的认证信息
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        // 重置认证信息
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public User rechargeToWallet(User user) {
        User currentUser = LoginUtils.getCurrentUser();
        currentUser.setBalance(currentUser.getBalance() + user.getBalance());
        userMapper.updateBalance(currentUser);
        //更新本地用户
        user = userMapper.getUserById(currentUser.getId());
        LoginUtils.setCurrentUser(user);
        return user;
    }
}
