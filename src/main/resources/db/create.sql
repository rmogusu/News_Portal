SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR,
 totalNumber int
);

CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
 general VARCHAR
 department VARCHAR
);

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 role VARCHAR,
 email VARCHAR,
 badgeNo int,
 department VARCHAR,
 departmentId INTEGER
);