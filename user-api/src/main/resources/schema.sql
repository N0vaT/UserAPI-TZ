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
    owner_id integer NOT NULL,
    CONSTRAINT pk_phone PRIMARY KEY (phone_id),
    CONSTRAINT fk_phones_owner FOREIGN KEY (owner_id) REFERENCES tz_users (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tz_emails
(
    email_id serial NOT NULL,
    email varchar(30) NOT NULL,
    date_of_addition timestamp NOT NULL,
    owner_id integer NOT NULL,
    CONSTRAINT pk_email PRIMARY KEY (email_id),
    CONSTRAINT fk_email_owner FOREIGN KEY (owner_id) REFERENCES tz_users (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);