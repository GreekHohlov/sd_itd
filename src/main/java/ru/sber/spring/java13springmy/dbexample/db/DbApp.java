package ru.sber.spring.java13springmy.dbexample.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.sber.spring.java13springmy.dbexample.constants.DataBaseConsts.*;

public enum DbApp {
    INSTANCE;
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:postgresql://" + DB_HOST + ":" + PORT + "/" + DB,
                    USER,
                    PASSWORD);
        }
        return connection;
    }
}
