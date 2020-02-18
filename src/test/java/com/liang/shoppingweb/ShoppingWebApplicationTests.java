package com.liang.shoppingweb;

import com.liang.shoppingweb.entity.cart.CertVo;
import com.liang.shoppingweb.mapper.CertGoodsMapper;
import com.liang.shoppingweb.mapper.UserMapper;
import com.liang.shoppingweb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SpringBootTest
class ShoppingWebApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Resource
    private CertGoodsMapper certGoodsMapper;

    @Test
    void r() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tbl_user");
        while (resultSet.next()){
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getInt(2));
            System.out.println(resultSet.getInt(3));
            System.out.println(resultSet.getString("email"));
        }
        resultSet.close();
        connection.close();
    }

    @Test
    void r1(){
        System.out.println(userMapper.getAll());
    }

    @Test
    void rename(){
        File picdir = new File("C:\\Users\\Administrator\\Downloads\\aaa\\pic");
        File[] files = picdir.listFiles();
        int i = 1;
        for (File pic :
                files) {
//            pic.renameTo(i+"");
            pic.renameTo(new File("C:\\Users\\Administrator\\Downloads\\aaa\\pic\\"+i+".jpg"));
            i++;
        }

    }

    @Test
    void password(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123");
        System.out.println(encode);
    }

    @Test
    void updatelastdate(){
        userService.updateLastLoginDateByUsername("444");
    }

    @Test
    void testCertGoodsMapper(){
        List<CertVo> certs = certGoodsMapper.getCertWithGoodsInfoByUsername("444");
        System.out.println(certs);
    }
}
