package ru.sber.spring.java13springmy.dbexample.dao;

import ru.sber.spring.java13springmy.dbexample.db.DbApp;
import ru.sber.spring.java13springmy.dbexample.model.Book;

import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BookDaoJDBC {
    //select * from book where id = ?
    //JDBC - Java DataBase Connection
    public Book findBookById(Integer bookId) {
        try (Connection connection = DbApp.INSTANCE.getConnection()) {
            if (connection != null) {
                System.out.println("ура! мы подключились к БД!!!!");
            } else {
                System.out.println("база данных отдыхает, не трогайте!");
            }
            PreparedStatement selectQuery = connection.prepareStatement("select * from books where id = ?");
            selectQuery.setInt(1, bookId);
            ResultSet resultSet = selectQuery.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setBookAuthor(resultSet.getString("author"));
                book.setBookTitle(resultSet.getString("title"));
                book.setDateAdded(resultSet.getDate("date_added"));
                System.out.println(book);
                return book;
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }
/*
    public List<Book> findBooksByTitle(String title) throws SQLException {
        try (Connection connection = DbApp.INSTANCE.getConnection()) {
            PreparedStatement selectQuery = connection.prepareStatement("select * from books where title like '%'||?||'%'");
            selectQuery.setString(1, title);
            ResultSet resultSet = selectQuery.executeQuery();
        }
        return Collections.emptyList();
    }
*/
//    private Connection newConnection() throws SQLException {
//        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/local_db",
//                "postgres", "12345");
//    }
}


