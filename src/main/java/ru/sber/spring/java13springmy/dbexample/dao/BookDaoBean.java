package ru.sber.spring.java13springmy.dbexample.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.sber.spring.java13springmy.dbexample.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("prototype")
public class BookDaoBean {
    private final Connection connection;

    public BookDaoBean(Connection connection) {
        this.connection = connection;
    }

    public Book findBookById(Integer bookId) throws SQLException {
        PreparedStatement selectQuery = connection.prepareStatement("select * from books where id = ?");
        selectQuery.setInt(1, bookId);
        ResultSet resultSet = selectQuery.executeQuery();
        Book book = new Book();
        while (resultSet.next()) {
            book.setBookAuthor(resultSet.getString("author"));
            book.setBookTitle(resultSet.getString("title"));
            book.setDateAdded(resultSet.getDate("date_added"));
        }
        return book;
    }
}
