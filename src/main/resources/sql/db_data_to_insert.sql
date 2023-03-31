-- select *
-- from users u
-- where u.last_name ilike :?;


-- select distinct t.*
-- from tasks t
--          left join category c on t.category = c.id
--          left join users u on t.user_id = u.id
--          join users w on t.worker_id = w.id
-- where t.name_task ilike :nameTask
--   and cast(t.id as char) ilike :id
--   and cast(t.status as char) like :statusTask
--   and c.name_category ilike :nameCategory
--   and u.last_name ilike :userFio
--   and w.last_name ilike :workerFio;

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
values (1, null, null, null, null, false, 'Системная категория', null),
       (2, null, null, null, null, false, 'Администрирование систем', null),
       (3, null, null, null, null, false, 'Адм. серверного оброруд.', 1),
       (4, null, null, null, null, false, 'Адм. сетевого оборуд.', 1),
       (5, null, null, null, null, false, 'Тоговое оборудование', null),
       (6, null, null, null, null, false, 'Приобретение', null);

insert into locations
values (1, null, null, null, null, false, 'Центральный офис'),
       (2, null, null, null, null, false, 'Главный склад'),
       (3, null, null, null, null, false, 'Магазин');

insert into users
values (1, null, null, null, null, false, null, 'admin@servicedesk.ru', 'учетная', 'Системная', 'service', 'запись',
        '$2a$10$HHdPd716i5B6Ci5qdNJMoe.Yhl7it3MxX8rU0JzPeRAc4kd5HpqNu', null, false, 1, 3),
       (2, null, null, null, null, false, null, 'ivanov@mail.ru', 'Иван', 'Иванов', 'ivanov', 'Иванович', '123',
        '+79235555656', false, 1, 3),
       (3, null, null, null, null, false, null,'petrov@mail.ru', 'Петр', 'Петров', 'petrov', 'Петрович', '123',
        '+79235555677',true, 1, 3),
       (4, null, null, null, null, false, null, 'sidorov@mail.ru', 'Сидор', 'Сидоров', 'sidorov', 'Сидорович', '123',
        '+79235555688',false, 1, 3),
       (5, null, null, null, null, false, null, 'greekkk@mail.ru', 'Борис', 'Степаненко', 'greek', 'Алексеевич',
       '$2a$10$fm.NURcqO0l4W4jmvotQjO6abp0QCt3BKrIoI.mZ6ruyNUB9fS5r2', '+79235555688',false, 2, 1);

insert into tasks
values (1, null, null, null, null, false, '2022-11-15 13:46:11.797607',null, 'Не работает ноутбук, переодичеки синий экран',
        '2022-12-15 13:46:11.797607', null, 'Не работает ноутбук', 2, 2, 1, 2, 2, 3);