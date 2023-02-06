package ru.sber.spring.java13springmy.dbexample.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {
 //   @Setter(AccessLevel.NONE)
    private Integer id;
    private String surname;
    private String nameU;
    private LocalDate birth_date;
    private String phone;
    private String mail;
    private String list_books;

}
