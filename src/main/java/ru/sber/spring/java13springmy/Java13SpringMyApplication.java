package ru.sber.spring.java13springmy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.spring.java13springmy.dbexample.MyDBApplicationContext;
import ru.sber.spring.java13springmy.dbexample.dao.BookDaoBean;
import ru.sber.spring.java13springmy.dbexample.dao.BookDaoJDBC;
import ru.sber.spring.java13springmy.dbexample.dao.UserDao;
import ru.sber.spring.java13springmy.dbexample.model.Book;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.sber.spring.java13springmy.dbexample.model.User;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class Java13SpringMyApplication implements CommandLineRunner {
/*
    private BookDaoBean bookDaoBean;
    private final UserDao userDao;

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Autowired
    public void setBookDaoBean(BookDaoBean bookDaoBean) {
        this.bookDaoBean = bookDaoBean;
    }


    public Java13SpringMyApplication(BookDaoBean bookDaoBean, UserDao userDao) {
        this.bookDaoBean = bookDaoBean;
        this.userDao = userDao;
    }


 */

    public static void main(String[] args) {
        SpringApplication.run(Java13SpringMyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/*
        BookDaoJDBC bookDaoJDBC = new BookDaoJDBC();
        bookDaoJDBC.findBookById(1);
        System.out.println("++++++++++++++++++++");

//        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyDBApplicationContext.class);
//        BookDaoBean bookDaoBean =ctx.getBean(BookDaoBean.class);
//        System.out.println(bookDaoBean.findBookById(1));
        System.out.println(bookDaoBean.findBookById(1));
        System.out.println("++++++++++++++++++++");


        List<Book> books = jdbcTemplate.query("select * from books",
                (rs, rowNum) -> new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDate("date_added")
                ));
        books.forEach(System.out::println);

/*
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyDBApplicationContext.class);
        UserDao userDao = ctx.getBean(UserDao.class);

 */

       // User user1 = new User(1, "Иванов", "Пётр", LocalDate.of(1982, 12, 10),
         //       "89965035031", "ivanov@mail.ru", "Недоросль, Доктор Живаго");
        /*
        userDao.addUser(new User(1, "Иванов", "Пётр", LocalDate.of(1982, 12, 10),
                "89965035031", "ivanov@mail.ru", "Недоросль, Доктор Живаго"));

         */

    }
}
