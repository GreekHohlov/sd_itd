--.Иерархические (рекурсивные) запросы
-- https://habr.com/ru/articles/73700/
--https://devmark.ru/article/postgres-recursive-query
--https://www.youtube.com/watch?v=2D51Hhbpk4s

--Рекурсивный запрос для категорий
with recursive temp1  (id, parent_category_id, name_category, path) as (
    select t1.id, t1.parent_category_id, t1.name_category, cast (t1.name_category as varchar(200)) as path
    from category t1 where t1.parent_category_id IS NULL
     union
     select t2.id, t2.parent_category_id, t2.name_category, cast (temp1.path || '->' || t2.name_category as varchar(200))
     from category t2 join temp1 on (temp1.id= t2.parent_category_id))
     select *from temp1;


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

--Роли
insert into role
values (1, null, null, null, null, false, 'Пользователь', 'USER'),
       (2, null, null, null, null, false, 'Сотрудник-исполнитель', 'EXECUTOR'),
       (3, null, null, null, null, false, 'Вед. сотрудник-исполнитель', 'MAIN_EXECUTOR'),
       (4, null, null, null, null, false, 'Администратор', 'ADMIN');

--Группы
insert into groups
values (nextval('groups_seq'), null, null, null, null, false, 'Пользователи'),
       (nextval('groups_seq'), null, null, null, null, false, 'Техническая поддержка'),
       (nextval('groups_seq'), null, null, null, null, false, 'Системные администраторы'),
       (nextval('groups_seq'), null, null, null, null, false, 'Сервис-инженеры'),
       (nextval('groups_seq'), null, null, null, null, false, 'Программисты 1C'),
       (nextval('groups_seq'), null, null, null, null, false, 'Программисты Java'),
       (nextval('groups_seq'), null, null, null, null, false, 'DevOps');

--SLA
insert into sla
values (nextval('sla_info_seq'), null, null, null, null, false, 2, 'Инциндент (высокий)', 1),
       (nextval('sla_info_seq'), null, null, null, null, false, 4, 'Инциндент (обычный)', 1),
       (nextval('sla_info_seq'), null, null, null, null, false, 8, 'Инциндент (низкий)', 2),
       (nextval('sla_info_seq'), null, null, null, null, false, 8, 'Обслуживание (высокий)', 2),
       (nextval('sla_info_seq'), null, null, null, null, false, 16, 'Обслуживание (обычный)', 4),
       (nextval('sla_info_seq'), null, null, null, null, false, 40, 'Обслуживание (низкий)', 8),
       (nextval('sla_info_seq'), null, null, null, null, false, 16, 'Изменение ПО (высокий)', 8),
       (nextval('sla_info_seq'), null, null, null, null, false, 32, 'Изменение ПО (средний)', 8),
       (nextval('sla_info_seq'), null, null, null, null, false, 64, 'Изменение ПО (низкий)', 8),
       (nextval('sla_info_seq'), null, null, null, null, false, 16, 'Доступ', 4),
       (nextval('sla_info_seq'), null, null, null, null, false, 240, 'Приобретение', 16);

--Типы заявок
insert into type_task
values (nextval('task_type_seq'), null, null, null, null, false, 'Инциндент (общий)', 2),
       (nextval('task_type_seq'), null, null, null, null, false, 'Инциндент (Админ. систем)', 1),
       (nextval('task_type_seq'), null, null, null, null, false, 'Инциндент (ПО Офиса)', 1),
       (nextval('task_type_seq'), null, null, null, null, false, 'Обслуживание (общее)', 5),
       (nextval('task_type_seq'), null, null, null, null, false, 'Обслуживание (Админ. систем)', 4),
       (nextval('task_type_seq'), null, null, null, null, false, 'Обслуживание (ПО Офиса)', 4),
       (nextval('task_type_seq'), null, null, null, null, false, 'Изменение ПО (1С)', 8),
       (nextval('task_type_seq'), null, null, null, null, false, 'Изменение ПО (Java)', 8),
       (nextval('task_type_seq'), null, null, null, null, false, 'Доступ', 10),
       (nextval('task_type_seq'), null, null, null, null, false, 'Приобритение', 11);

--Категории заявок
insert into category
values (nextval('category_seq'), null, null, null, null, false, 'Общая категория', null),
       (nextval('category_seq'), null, null, null, null, false, 'Администрирование систем', 1),
       (nextval('category_seq'), null, null, null, null, false, 'Адм. серверного оброруд.', 2),
       (nextval('category_seq'), null, null, null, null, false, 'Адм. сетевого оборуд.', 2),
       (nextval('category_seq'), null, null, null, null, false, 'IP-телефония', null),
       (nextval('category_seq'), null, null, null, null, false, 'Почта', null),
       (nextval('category_seq'), null, null, null, null, false, 'Торговое обруд.', null),
       (nextval('category_seq'), null, null, null, null, false, 'Адм. ЭДО', null),
       (nextval('category_seq'), null, null, null, null, false, 'Тоговое оборудование', null),
       (nextval('category_seq'), null, null, null, null, false, 'Приобретение', null);

--Площадки
insert into locations
values (nextval('workers_seq'), null, null, null, null, false, 'Центральный офис'),
       (nextval('workers_seq'), null, null, null, null, false, 'Офис Иркутск'),
       (nextval('workers_seq'), null, null, null, null, false, 'Офис Владивосток'),
       (nextval('workers_seq'), null, null, null, null, false, 'Склад Якутия'),
       (nextval('workers_seq'), null, null, null, null, false, 'Офис Москва'),
       (nextval('workers_seq'), null, null, null, null, false, 'Главный склад'),
       (nextval('workers_seq'), null, null, null, null, false, 'Магазин Добрянка-1'),
       (nextval('workers_seq'), null, null, null, null, false, 'Магазин Добрянка-2'),
       (nextval('workers_seq'), null, null, null, null, false, 'Магазин Бахетле-1'),
       (nextval('workers_seq'), null, null, null, null, false, 'Магазин Бахетле-3');

insert into users
values
    --Исполнители
    (nextval('users_seq'), null, null, null, null, false, null, 'admin@servicedesk.ru', 'Системная', 'Учётная',
     'A_service', 'Запись', '$2a$10$HHdPd716i5B6Ci5qdNJMoe.Yhl7it3MxX8rU0JzPeRAc4kd5HpqNu',
     '+7991991919', true, 2, 1, 2),
    (nextval('users_seq'), null, null, null, null, false, null, 'ignatevvk@mail.ru', 'Вячеслав', 'Игнатьев',
     'Ex_ignatevvk', 'Константинович', '$2a$10$aQu2wEVfAZ5pOUsmARmA0e3c4LX5QXNhEUyxCrN5rca5yZtWw8GUK',
     '+79915035031', true, 2, 1, 2),
    (nextval('users_seq'), null, null, null, null, false, null, 'greekkk@mail.ru', 'Борис', 'Степаненко', 'Ex_greek',
     'Алексеевич', '$2a$10$fm.NURcqO0l4W4jmvotQjO6abp0QCt3BKrIoI.mZ6ruyNUB9fS5r2',
     '+79235555688', true, 2, 1, 2),
    --Пользователи
    (nextval('users_seq'), null, null, null, null, false, null, 'ivanov@mail.ru', 'Иван', 'Иванов', 'ivanov',
     'Иванович', '$2a$10$6ZdDup03pD/2d2WqozOEtuGDU6yHj28jcUqQk.ocNGAYQNJ0c4NNq', '+79235555656', false, 1, 1, 1),
    (nextval('users_seq'), null, null, null, null, false, null, 'petrov@mail.ru', 'Петр', 'Петров', 'petrov',
     'Петрович', '$2a$10$6ZdDup03pD/2d2WqozOEtuGDU6yHj28jcUqQk.ocNGAYQNJ0c4NNq', '+79235555677', false, 1, 6, 1),
    (nextval('users_seq'), null, null, null, null, false, null, 'sidorov@mail.ru', 'Сидор', 'Сидоров', 'sidorov',
     'Сидорович', '$2a$10$6ZdDup03pD/2d2WqozOEtuGDU6yHj28jcUqQk.ocNGAYQNJ0c4NNq', '+79235555688', false, 1, 7, 1),
    (nextval('users_seq'), null, null, null, null, false, null, 'smirnov@mail.ru', 'Алексей', 'Смирнов', 'smirnov',
     'Сергеевич', '$2a$10$6ZdDup03pD/2d2WqozOEtuGDU6yHj28jcUqQk.ocNGAYQNJ0c4NNq', '+79235555689', false, 1, 5, 1),
    (nextval('users_seq'), null, null, null, null, false, null, 'novikov@mail.ru', 'Михаил', 'Новиков', 'novikov',
     'Петрович', '$2a$10$6ZdDup03pD/2d2WqozOEtuGDU6yHj28jcUqQk.ocNGAYQNJ0c4NNq', '+79235555693', false, 1, 1, 1),
    (nextval('users_seq'), null, null, null, null, false, null, 'putin@mail.ru', 'Владимир', 'Путин', 'putin',
     'Владимирович', '$2a$10$6ZdDup03pD/2d2WqozOEtuGDU6yHj28jcUqQk.ocNGAYQNJ0c4NNq', '+79235555695', false, 1, 5, 1),
    (nextval('users_seq'), null, null, null, null, false, null, 'medvedev@mail.ru', 'Дмитрий', 'Медведев', 'medvedev',
     'Владимирович', '$2a$10$6ZdDup03pD/2d2WqozOEtuGDU6yHj28jcUqQk.ocNGAYQNJ0c4NNq', '+79235555675', false, 1, 5, 1);

--Заявки
insert into tasks
values (nextval('tasks_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        '2023-04-04 10:25:10.702359', null,
        'Не работает ноутбук, переодичеки синий экран', null, null, 'Не работает ноутбук', 1, 2, 10, 5, 4, 2),
       (nextval('tasks_seq'), 'Starting loading', '2023-03-30 10:25:10.702359', null, null, false,
        '2023-04-03 10:25:10.702359', null,
        'Не печатает принтер в бухгалтерии', null, null, 'Проблема с печатью', 1, 1, 4, 2, 5, 2),
       (nextval('tasks_seq'), 'Starting loading', '2023-04-01 10:25:10.702359', null, null, false,
        '2023-04-03 10:25:10.702359', null,
        'Не работает почта', null, null, 'Не отправляются письма! Почта почищена.', 2, 1, 6, 3, 6,
        3),
       (nextval('tasks_seq'), 'Starting loading', '2023-04-01 12:25:10.702359', null, null, false,
        '2023-04-02 10:25:10.702359', null,
        'Отсуствует адесная книга', null, null, 'Прошу загрузить адресную книгу', 2, 1, 6, 3, 7,
        3),
       (nextval('tasks_seq'), 'Starting loading', '2023-04-01 12:25:10.702359', null, null, false,
        '2023-04-02 10:25:10.702359', null,
        'Отсуствует архив почты', null, null, 'Прошу загрузить архив почты', 2, 2, 6, 3, 6,
        3),
       (nextval('tasks_seq'), 'Starting loading', '2023-04-01 12:25:10.702359', null, null, false,
        '2023-04-03 10:25:10.702359', null, 'Доработка модуля оповещения в Java', null, null,
        'Доработать модуль оповещения в статусе заявки', 2, 2, 6, 3, 6, 3);

--История заявок
insert into history
values (nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Создана заявка: Starting loading', 1),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Создана заявка: Starting loading', 2),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Создана заявка: Starting loading', 3),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Создана заявка: Starting loading', 4),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Создана заявка: Starting loading', 5),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Создана заявка: Starting loading', 6),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Изменен статус: Заявка в работе. Степаненко Б А', 1),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Изменен статус: Заявка в работе. Игнатьев В К', 2),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Изменен статус: Заявка в работе. Степаненко Б А', 3),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Изменен статус: Заявка в работе. Степаненко Б А', 4),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Изменен статус: Заявка остановлена. Обоснование: Тестирование сервиса Степаненко Б А', 5),
(nextval('history_seq'), 'Starting loading', '2023-03-31 10:25:10.702359', null, null, false,
        'Изменен статус: Заявка остановлена. Обоснование: Доп тестирование Степаненко Б А', 6)