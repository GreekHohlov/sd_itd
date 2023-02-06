package ru.sber.spring.java13springmy.dbexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.sber.spring.java13springmy.dbexample.dao.BookDaoBean;
import ru.sber.spring.java13springmy.dbexample.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.sber.spring.java13springmy.dbexample.constants.DataBaseConsts.*;

@Configuration

public class MyDBApplicationContext {

    @Bean
    @Scope("singleton")
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://" + DB_HOST + ":" + PORT + "/" + DB,
                USER, PASSWORD);
    }

//    @Bean
//    public BookDaoBean bookDaoBean() throws SQLException {
//        return new BookDaoBean(getConnection());
//    }
/*
    @Bean
    @Scope("prototype")
    public UserDao userDao() throws SQLException {
        return new UserDao(getConnection());
    }
*/

}
