SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR,
 totalNumber INTEGER
);

CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
 general VARCHAR,
 department VARCHAR
);

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 position VARCHAR,
 role VARCHAR,
 email VARCHAR,
 badgeNo INTEGER ,
 department VARCHAR,
 departmentId INTEGER
);
CREATE TABLE IF NOT EXISTS departments_news (
 id int PRIMARY KEY auto_increment,
 newsId INTEGER,
 departmentId INTEGER
);