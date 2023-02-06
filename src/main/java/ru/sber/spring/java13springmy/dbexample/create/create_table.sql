create table client
(
    id         serial primary key,
    surname    varchar(30) not null,
    nameU       varchar(30) not null,
    birth_date date        not null,
    phone      varchar(12),
    mail       varchar(30),
    list_books varchar (200)
);

drop table client;