package com.liang.shoppingweb;

import com.liang.shoppingweb.entity.cart.CertVo;
import com.liang.shoppingweb.entity.order.OrderCell;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.CertGoodsMapper;
import com.liang.shoppingweb.mapper.OrderCellMapper;
import com.liang.shoppingweb.mapper.UserMapper;
import com.liang.shoppingweb.service.OrderService;
import com.liang.shoppingweb.service.UserService;
import com.liang.shoppingweb.utils.LoginUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShoppingWebApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Resource
    private CertGoodsMapper certGoodsMapper;

    @Autowired
    private OrderService orderService;

    @Resource
    private OrderCellMapper orderCellMapper;

    @BeforeAll
    @Test
    void initLogin(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = new User();
        user.setUsername("root");
        session.setAttribute("SW_USER",user);
    }

    @Test
    void testCreateOrder() {
        Integer[] ids = {41};
        try {
            orderService.createOrder(ids);
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void testMultiInsert() {
        List<CertVo> certVos = certGoodsMapper.getCertWithGoodsInfoByIds("(35,38)");
        List<OrderCell> orderCells = new ArrayList<>();
        for (CertVo certVo : certVos) {
            orderCells.add(certVo.convertToOrderCell());
        }
//        orderCellMapper.insertOrderCells(orderCells);
    }

    @Test
    void r() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tbl_user");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getInt(2));
            System.out.println(resultSet.getInt(3));
            System.out.println(resultSet.getString("email"));
        }
        resultSet.close();
        connection.close();
    }

    @Test
    void r1() {
        System.out.println(userMapper.getAll());
    }

    @Test
    void rename() {
        File picdir = new File("C:\\Users\\Administrator\\Downloads\\aaa\\pic");
        File[] files = picdir.listFiles();
        int i = 1;
        for (File pic :
                files) {
//            pic.renameTo(i+"");
            pic.renameTo(new File("C:\\Users\\Administrator\\Downloads\\aaa\\pic\\" + i + ".jpg"));
            i++;
        }

    }

    @Test
    void password() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123");
        System.out.println(encode);
    }

    @Test
    void updatelastdate() {
        userService.updateLastLoginDateByUsername("444");
    }

    @Test
    void testCertGoodsMapper() {
        List<CertVo> certs = certGoodsMapper.getCertWithGoodsInfoByUsername("444");
        System.out.println(certs);
    }


}
