package ru.sber.spring.java13springmy.dbexample.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.dbexample.model.User;

import java.sql.*;

@Component
@Scope("prototype")
public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into client (surname, nameU, birth_date, phone, mail, list_books) " +
                "VALUES (?, ?, ?, ?, ?, ?)");
        // String sql = String.format("insert into client ('id','surname', 'name', 'birth_date date', 'phone', 'mail', 'list_books') " +
        //              "VALUES ('%s','%s','%s','%s','%s','%s','%s')",

        statement.setString(1, user.getSurname());
        statement.setString(2, user.getNameU());
        statement.setDate(3, Date.valueOf(user.getBirth_date()));
        statement.setString(4, user.getPhone());
        statement.setString(5, user.getMail());
        statement.setString(6, user.getList_books());
        statement.executeUpdate();
    }
}
