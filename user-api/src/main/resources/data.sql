INSERT INTO tz_users(login, date_of_creation)
VALUES ('Max', '2023-01-16 16:00:00'),
       ('Nikita', '2023-01-17 16:00:00'),
       ('Маряи', '2023-01-18 16:00:00'),
       ('Владимир', '2023-01-19 16:00:00'),
       ('Anna', '2023-01-20 16:00:00'),
       ('Антон', '2023-01-21 16:00:00');

INSERT INTO tz_emails(email, date_of_addition, owner_id)
VALUES ('Max@mail.ru', '2023-01-16 16:00:00', 1),
       ('Max@yandex.ru', '2023-01-16 17:00:00', 1),
       ('m@yandex.ru', '2023-01-18 16:00:00', 3),
       ('Anna@gmail.ru', '2023-01-19 16:00:00', 5),
       ('Anna@yandex.ru', '2023-01-20 16:00:00', 5),
       ('Aaaaa@mail.com', '2023-01-21 16:00:00', 5);