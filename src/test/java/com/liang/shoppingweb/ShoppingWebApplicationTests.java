package com.liang.shoppingweb;

import com.liang.shoppingweb.entity.cart.CartVo;
import com.liang.shoppingweb.entity.common.Dictionary;
import com.liang.shoppingweb.entity.order.OrderCell;
import com.liang.shoppingweb.entity.order.OrderVo;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.cart.CartVoMapper;
import com.liang.shoppingweb.mapper.user.UserMapper;
import com.liang.shoppingweb.service.common.DictionaryService;
import com.liang.shoppingweb.service.order.OrderService;
import com.liang.shoppingweb.service.order.OrderVoService;
import com.liang.shoppingweb.service.user.UserService;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private CartVoMapper cartVoMapper;

    @Autowired
    private OrderService orderService;

//    @Resource
//    private OrderCellMapper orderCellMapper;

    @Resource
    private OrderVoService orderVoService;

    @Autowired
    private DictionaryService dictionaryService;

    @BeforeAll
    @Test
    void initLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = new User();
        user.setUsername("444");
        session.setAttribute("SW_USER", user);
    }

    @Test
    void insertDictionary() {
        List<Dictionary> dictionaries1 = new ArrayList<>();
        List<Dictionary> dictionaries2;
        for (int i = 0; i < 10; i++) {
            Dictionary dictionary = new Dictionary();
            dictionary.setName("种类" + i);
            dictionary.setId(UUID.randomUUID().toString());
            dictionary.setCreateDate(new Date());
            dictionary.setValue(i + "");
            dictionary.setOrder(i+1);
            dictionaries1.add(dictionary);
            dictionaries2 = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Dictionary dictionary2 = new Dictionary();
                dictionary2.setName("类型" + j);
                dictionary2.setId(UUID.randomUUID().toString());
                dictionary2.setCreateDate(new Date());
                dictionary2.setValue(j + "");
                dictionary2.setOrder(j+1);
                dictionary2.setParentId(dictionary.getId());
                dictionaries2.add(dictionary2);
            }
            dictionaryService.batchAddDictionary(dictionaries2, 2);
        }
        dictionaryService.batchAddDictionary(dictionaries1, 1);
    }

    @Test
    void testOrderVoService() throws Exception {
        List<OrderVo> orderVo = orderVoService.getUnFinishOrderVoByUserId();
        System.out.println(orderVo);
    }

    @Test
    void testCreateOrder() {
        String[] ids = {"41"};
        try {
            orderService.createOrder(ids, "1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void testMultiInsert() {
        List<CartVo> cartVos = cartVoMapper.getCartWithGoodsInfoByIds("(35,38)");
        List<OrderCell> orderCells = new ArrayList<>();
        for (CartVo cartVo : cartVos) {
            orderCells.add(cartVo.convertToOrderCell());
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
    void testCartGoodsMapper() {
        List<CartVo> carts = cartVoMapper.getCartWithGoodsInfoByUserId("7ba510b1-9bc9-4748-9fe1-cff83eafeb28");
        System.out.println(carts);
    }


}
