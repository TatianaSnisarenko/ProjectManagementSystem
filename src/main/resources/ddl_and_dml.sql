CREATE DATABASE homework_db;

CREATE TABLE companies
(
    id_company SERIAL,
    name       varchar(50) NOT NULL,
    city       varchar(50) DEFAULT NULL,
    PRIMARY KEY (id_company)
);

create type sex_choice as enum ('male','female');
create type language_choice as enum ('Java', 'C++', 'C#', 'JS');
create type level_choice as enum ('Junior','Middle','Senior');

CREATE TABLE developers
(
    id_developer SERIAL,
    name         varchar(50) NOT NULL,
    age          int DEFAULT NULL,
    sex          sex_choice  NOT NULL,
    id_company   int         NOT NULL,
    PRIMARY KEY (id_developer),
    FOREIGN KEY (id_company) REFERENCES companies (id_company)
);

CREATE TABLE skills
(
    id_skill SERIAL,
    language language_choice NOT NULL,
    level    level_choice    NOT NULL,
    PRIMARY KEY (id_skill)
);

CREATE TABLE developers_skills
(
    id_developer int NOT NULL,
    id_skill     int NOT NULL,
    PRIMARY KEY (id_developer, id_skill),
    FOREIGN KEY (id_developer) REFERENCES developers (id_developer),
    FOREIGN KEY (id_skill) REFERENCES skills (id_skill)
);

CREATE TABLE projects
(
    id_project  SERIAL,
    name        varchar(50) NOT NULL,
    description text,
    PRIMARY KEY (id_project)
);

CREATE TABLE developers_projects
(
    id_developer int NOT NULL,
    id_project   int NOT NULL,
    PRIMARY KEY (id_developer, id_project),
    FOREIGN KEY (id_developer) REFERENCES developers (id_developer),
    FOREIGN KEY (id_project) REFERENCES projects (id_project)
);

CREATE TABLE companies_projects
(
    id_company int NOT NULL,
    id_project int NOT NULL,
    PRIMARY KEY (id_company, id_project),
    FOREIGN KEY (id_company) REFERENCES companies (id_company),
    FOREIGN KEY (id_project) REFERENCES projects (id_project)
);

CREATE TABLE customers
(
    id_customer SERIAL,
    name        varchar(50) NOT NULL,
    city        varchar(50) DEFAULT NULL,
    PRIMARY KEY (id_customer)
);

CREATE TABLE customers_projects
(
    id_customer int NOT NULL,
    id_project  int NOT NULL,
    PRIMARY KEY (id_customer, id_project),
    FOREIGN KEY (id_customer) REFERENCES customers (id_customer),
    FOREIGN KEY (id_project) REFERENCES projects (id_project)
);

INSERT INTO companies(name, city)
VALUES ('Global logic', 'Kiev'),
       ('Epam', 'Dnipro'),
       ('SoftServe', 'Kharkiv');

INSERT INTO developers(name, age, sex, id_company)
VALUES ('Tatiana Skazka', 28, 'female', 1),
       ('John Smith', 35, 'male', 2),
       ('Alina Kulkova', 23, 'female', 3),
       ('Snegana Egorkina', 29, 'female', 1),
       ('Sergey Smely', 42, 'male', 2),
       ('Polina Jukova', 38, 'female', 3),
       ('Alex Rodgers', 39, 'male', 1),
       ('Sonya Strigina', 24, 'female', 2),
       ('Paul Macknale', 37, 'male', 3),
       ('Zlata Zorova', 31, 'female', 3),
       ('Shon Sparks', 33, 'male', 2),
       ('Mognich Zbarov', 27, 'male', 1);

INSERT INTO skills(language, level)
VALUES ('Java', 'Junior'),
       ('Java', 'Middle'),
       ('Java', 'Senior'),
       ('C++', 'Junior'),
       ('C++', 'Middle'),
       ('C++', 'Senior'),
       ('C#', 'Junior'),
       ('C#', 'Middle'),
       ('C#', 'Senior'),
       ('JS', 'Junior'),
       ('JS', 'Middle'),
       ('JS', 'Senior');

INSERT INTO projects(name, description)
VALUES ('alfa', 'cool project'),
       ('security', 'serious project'),
       ('cloud', 'agile cloud sevice'),
       ('sfinks', 'stable bank project'),
       ('croud', 'new social media'),
       ('rumors', 'new twitter'),
       ('topics', 'new instagram');

INSERT INTO developers_projects
VALUES (1, 1),
       (5, 1),
       (3, 2),
       (5, 2),
       (2, 3),
       (8, 3),
       (4, 4),
       (12, 4),
       (6, 5),
       (10, 5),
       (7, 6),
       (11, 6),
       (1, 7),
       (3, 7),
       (5, 7);

INSERT INTO companies_projects
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (1, 4),
       (2, 5),
       (3, 6),
       (1, 7);

INSERT INTO developers_skills
VALUES (3, 1),
       (1, 2),
       (5, 3),
       (9, 3),
       (8, 4),
       (4, 5),
       (12, 5),
       (2, 6),
       (10, 8),
       (6, 9),
       (11, 11),
       (7, 12);

INSERT INTO customers(name, city)
VALUES ('Omega', 'Kiev'),
       ('Mirny', 'Los-Angeles'),
       ('StarLight', 'New York'),
       ('Belany', 'Las Vegas'),
       ('NnjunJan', 'Sentoza'),
       ('Koala', 'Dnipro'),
       ('Big Bus', 'London');

INSERT INTO customers_projects
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7);

alter table developers
    add column salary double precision;
UPDATE developers
SET salary = 3000
WHERE (id_developer = 1);
UPDATE developers
SET salary = 5000
WHERE (id_developer = 2);
UPDATE developers
SET salary = 800
WHERE (id_developer = 3);
UPDATE developers
SET salary = 2500
WHERE (id_developer = 4);
UPDATE developers
SET salary = 5000
WHERE (id_developer = 5);
UPDATE developers
SET salary = 5500
WHERE (id_developer = 6);
UPDATE developers
SET salary = 5400
WHERE (id_developer = 7);
UPDATE developers
SET salary = 800
WHERE (id_developer = 8);
UPDATE developers
SET salary = 6000
WHERE (id_developer = 9);
UPDATE developers
SET salary = 2200
WHERE (id_developer = 10);
UPDATE developers
SET salary = 2100
WHERE (id_developer = 11);
UPDATE developers
SET salary = 2300
WHERE (id_developer = 12);

alter table projects
    add column cost double precision;
UPDATE projects
SET cost = 8000
WHERE (id_project = 1);
UPDATE projects
SET cost = 5800
WHERE (id_project = 2);
UPDATE projects
SET cost = 5800
WHERE (id_project = 3);
UPDATE projects
SET cost = 4800
WHERE (id_project = 4);
UPDATE projects
SET cost = 7700
WHERE (id_project = 5);
UPDATE projects
SET cost = 7500
WHERE (id_project = 6);
UPDATE projects
SET cost = 8800
WHERE (id_project = 7);

alter table projects
    add column creation_date date default current_date;