--РОЛИ
insert into role
values (1, null, null, null, null, false, 'Пользователь', 'USER'),
       (2, null, null, null, null, false, 'Сотрудник', 'EXECUTOR'),
       (3, null, null, null, null, false, 'Вед. сотрудник', 'MAIN_EXECUTOR'),
       (4, null, null, null, null, false, 'Администратор', 'ADMIN');

--Группы
insert into groups
values (1, null, null, null, null, false, 'Пользователи', 1),
       (2, null, null, null, null, false, 'Сотрудники', 2),
       (3, null, null, null, null, false, 'Администраторы', 3);
--SLA
insert into sla
values (1, null, null, null, null, false, 1, 'Инциндент (высокий)', 2),
       (2, null, null, null, null, false, 1, 'Инциндент (обычный)', 4),
       (3, null, null, null, null, false, 1, 'Инциндент (низкий)', 8),
       (4, null, null, null, null, false, 2, 'Обслуживание (высокий)', 8),
       (5, null, null, null, null, false, 4, 'Обслуживание (обычный)', 16),
       (6, null, null, null, null, false, 8, 'Обслуживание (низкий)', 40),
       (7, null, null, null, null, false, 4, 'Доступ', 16),
       (8, null, null, null, null, false, 16, 'Приобретение', 240);
--Типы заявок
insert into type_task
values (1, null, null, null, null, false, 'Инциндент Общий', 1),
       (2, null, null, null, null, false, 'Инциндент Адм.систем', 1),
       (3, null, null, null, null, false, 'Обслуживание ПО', 6),
       (4, null, null, null, null, false, 'Доступ', 7),
       (5, null, null, null, null, false, 'Приобретение ИТ', 8);

--Категории заявок
insert into category
values (1, null, null, null, null, false, 'Администрирование систем', null),
       (2, null, null, null, null, false, 'Адм. серверного оброруд.', 1),
       (3, null, null, null, null, false, 'Адм. сетевого оборуд.', 1),
       (4, null, null, null, null, false, 'Тоговое оборудование', null),
       (5, null, null, null, null, false, 'Приобретение', null);

insert into locations
values (1, null, null, null, null, false, 'Центральный офис'),
       (2, null, null, null, null, false, 'Главный склад'),
       (3, null, null, null, null, false, 'Магазин');

insert into users
values (2, null, null, null, null, false, null, 'ivanov@mail.ru', 'Иван', 'Иванов', 'ivanov', 'Иванович', '123',
        '+79235555656', false, 1, 3),
       (3, null, null, null, null, false, null,'petrov@mail.ru', 'Петр', 'Петров', 'petrov', 'Петрович', '123',
        '+79235555677',true, 1, 3),
       (4, null, null, null, null, false, null, 'sidorov@mail.ru', 'Сидор', 'Сидоров', 'sidorov', 'Сидорович', '123',
        '+79235555688',false, 1, 3);

insert into tasks
values (1, null, null, null, null, false, '2022-11-15 13:46:11.797607',null, 'Не работает ноутбук, переодичеки синий экран',
         '2022-12-15 13:46:11.797607', null, 'Не работает ноутбук', 2, 2, 1, 2, 2, 3);


--
-- --АВТОРЫ
-- truncate table books_authors;
-- truncate table authors cascade;
-- INSERT INTO authors (id, created_by, created_when, fio, description, birth_date)
-- VALUES (nextval('authors_seq'), 'admin', '2022-11-15 13:46:11.797607', 'Александр Грибоедов',
--         'Писатель Александр Грибоедов ' ||
--         'родился в 1795 году в Москве. С детства Александр был невероятно развитым мальчиком — в шесть
-- лет он знал уже три языка, а еще через 6 — еще три.', '1795-01-15');
-- INSERT INTO authors (id, created_by, created_when, fio, description, birth_date)
-- VALUES (nextval('authors_seq'), 'admin', '2022-11-15 13:47:02.414728', 'Джейн Остин',
--         'Остен Джейн — выдающий британский писатель, классик мировой литературы. Родилась в 1775 году в небольшом городке в графстве Хэмпшир. В семье кроме Джейн росло еще 7 детей. У будущей романистки было 6 братьев и сестра Кассандра. ',
--         '1775-12-16');
-- INSERT INTO authors (id, created_by, created_when, fio, description, birth_date)
-- VALUES (nextval('authors_seq'), 'admin', '2022-11-15 13:48:53.363059', 'Федор Достоевский',
--         'Гордость отечественной литературы Федор Михайлович Достоевский родился в Москве в 1821 году в семье врача. Почти все свое детство Федор и его шесть братьев и сестер провели внутри больничных стен на работе отца. Воспитывались дети в строгости.',
--         '1821-11-11');
-- INSERT INTO authors (id, created_by, created_when, fio, description, birth_date)
-- VALUES (nextval('authors_seq'), 'admin', '2022-11-15 13:50:12.953413', 'Эмили Бронте',
--         'член знаменитого литературного английского семейства, при жизни была, пожалуй, самой незаметной на фоне своих знаменитых сестры и брата. С детства отличаясь живым и ярким воображением',
--         '1818-07-30');
-- INSERT INTO authors (id, created_by, created_when, fio, description, birth_date)
-- VALUES (nextval('authors_seq'), 'admin', '2022-11-15 13:51:08.314682', 'Михаил Булгаков',
--         'Михаил Афанасьевич Булгаков появился на свет 15 мая (по старому стилю — 3 мая) 1891 года в Киеве. Его родители были преподавателями и сделали все, чтобы дать сыну блестящее образование.',
--         '1891-05-15');
-- INSERT INTO authors (id, created_by, created_when, fio, description, birth_date)
-- VALUES (nextval('authors_seq'), 'admin', '2022-11-15 13:51:08.314682', 'Илья Ильф',
--         'настоящее имя — Иехиел-Лейб бен Арьевич Файнзильберг; 1897—1937', '1897-01-01');
-- INSERT INTO authors (id, created_by, created_when, fio, description, birth_date)
-- VALUES (nextval('authors_seq'), 'admin', '2022-11-15 13:51:08.314682', 'Евгений Петров',
--         'настоящее имя — Евгений Петрович Катаев; 1902—1942', '1902-01-01');
-- --BOOKS
-- truncate table books cascade;
-- INSERT INTO books (id, created_by, created_when, amount, genre, online_copy_path, publish_date, storage_place, title,
--                    publish, page_count)
-- VALUES (nextval('books_seq'), 'admin', '2022-11-15 13:54:28.115079', 100, 3, null, '1966-01-01', '1-М',
--         'Мастер и Маргарита', 'журнал «Москва»', 416);
-- INSERT INTO books (id, created_by, created_when, amount, genre, online_copy_path, publish_date, storage_place, title,
--                    publish, page_count)
-- VALUES (nextval('books_seq'), 'admin', '2022-11-15 13:56:12.600618', 10, 3, null, '1925-01-01', '1-Б', 'Белая Гвардия',
--         'журнал «Россия»', 416);
-- INSERT INTO books (id, created_by, created_when, amount, genre, online_copy_path, publish_date, storage_place, title,
--                    publish, page_count)
-- VALUES (nextval('books_seq'), 'admin', '2022-11-15 13:57:05.231780', 11, 2, null, '2023-01-15', '17-Г',
--         'Грозовой перевал', 'Азбука, 2023 г.', 384);
-- INSERT INTO books (id, created_by, created_when, amount, genre, online_copy_path, publish_date, storage_place, title,
--                    publish, page_count)
-- VALUES (nextval('books_seq'), 'admin', '2022-11-15 13:57:43.883671', 11, 3, null, '2022-01-01', '1-И', 'Идиот', 'Эксмо',
--         636);
-- INSERT INTO books (id, created_by, created_when, amount, genre, online_copy_path, publish_date, storage_place, title,
--                    publish, page_count)
-- VALUES (nextval('books_seq'), 'admin', '2022-11-15 13:58:12.172216', 111, 2, null, '2022-03-22', '13-Г',
--         'Гордость и предубеждение', 'АСТ', 416);
-- INSERT INTO books (id, created_by, created_when, amount, genre, online_copy_path, publish_date, storage_place, title,
--                    publish, page_count)
-- VALUES (nextval('books_seq'), 'admin', '2022-11-15 13:58:12.172216', 110, 2, null, '2016-03-11', '1-Г', 'Горе от ума',
--         'Азбука', 256);
-- INSERT INTO books (id, created_by, created_when, amount, genre, online_copy_path, publish_date, storage_place, title,
--                    publish, page_count)
-- VALUES (nextval('books_seq'), 'admin', '2022-11-15 13:58:12.172216', 2, 3, null, '2016-03-11', '1-Г', 'Золотой теленок',
--         'Текст', 432);
--
-- --BOOKS_AUTHORS
-- INSERT INTO public.books_authors (book_id, author_id)
-- VALUES (14, 1);
-- INSERT INTO public.books_authors (book_id, author_id)
-- VALUES (13, 2);
-- INSERT INTO public.books_authors (book_id, author_id)
-- VALUES (12, 3);
-- INSERT INTO public.books_authors (book_id, author_id)
-- VALUES (11, 4);
-- INSERT INTO public.books_authors (book_id, author_id)
-- VALUES (10, 5);
-- INSERT INTO public.books_authors (book_id, author_id)
-- VALUES (9, 5);
-- INSERT INTO public.books_authors (book_id, author_id)
-- VALUES (15, 6);
-- INSERT INTO public.books_authors (book_id, author_id)
-- VALUES (15, 7);

