package ru.sber.spring.java13springmy.dbexample.model;

import lombok.*;

import java.util.Date;

//POJO - Plain Old Java Object
@Data
//@Setter
//@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Book {
    @Setter(AccessLevel.NONE)
    //@Getter(AccessLevel.NONE)
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
    private Date dateAdded;

}
