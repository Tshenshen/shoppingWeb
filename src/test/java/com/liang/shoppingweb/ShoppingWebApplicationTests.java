package com.liang.shoppingweb;

import com.liang.shoppingweb.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
class ShoppingWebApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Resource
    private UserMapper userMapper;

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
    void testPageHelper(){

    }
}
