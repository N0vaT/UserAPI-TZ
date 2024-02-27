DROP TABLE IF EXISTS tz_phones;
DROP TABLE IF EXISTS tz_emails;
DROP TABLE IF EXISTS tz_users;

CREATE TABLE IF NOT EXISTS tz_users
(
    user_id serial NOT NULL,
    login varchar(20) UNIQUE NOT NULL,
    date_of_creation timestamp NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS tz_phones
(
    phone_id serial NOT NULL,
    phone varchar(30) NOT NULL,
    date_of_addition timestamp NOT NULL,
    owner_id integer,
    CONSTRAINT pk_phone PRIMARY KEY (phone_id),
    CONSTRAINT fk_phones_owner FOREIGN KEY (owner_id) REFERENCES tz_users (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tz_emails
(
    email_id serial NOT NULL,
    email varchar(30) NOT NULL,
    date_of_addition timestamp NOT NULL,
    owner_id integer,
    CONSTRAINT pk_email PRIMARY KEY (email_id),
    CONSTRAINT fk_email_owner FOREIGN KEY (owner_id) REFERENCES tz_users (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO tz_users(login, date_of_creation)
VALUES ('Max', '2023-01-16 16:00:00'),
       ('Nikita', '2023-01-17 16:00:00'),
       ('Маряи', '2023-01-18 16:00:00'),
       ('Владимир', '2023-01-19 16:00:00'),
       ('Anna', '2023-01-20 16:00:00'),
       ('Антон', '2023-01-21 16:00:00');

INSERT INTO tz_phones(phone, date_of_addition, owner_id)
VALUES ('88888888888', '2023-01-16 16:00:00', 1),
       ('11111111111', '2023-01-16 17:00:00', 2),
       ('22222222222', '2023-01-18 16:00:00', 3),
       ('33333333333', '2023-01-19 16:00:00', 4),
       ('44444444444', '2023-01-20 16:00:00', 4),
       ('55555555555', '2023-01-21 16:00:00', 5);

INSERT INTO tz_emails(email, date_of_addition, owner_id)
VALUES ('Max@mail.ru', '2023-01-16 16:00:00', 1),
       ('Max@yandex.ru', '2023-01-16 17:00:00', 1),
       ('m@yandex.ru', '2023-01-18 16:00:00', 3),
       ('Anna@gmail.ru', '2023-01-19 16:00:00', 5),
       ('Anna@yandex.ru', '2023-01-20 16:00:00', 5),
       ('Aaaaa@mail.com', '2023-01-21 16:00:00', 5);