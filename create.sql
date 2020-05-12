CREATE DATABASE news_portal;
\c news_portal;
CREATE TABLE departments(id SERIAL PRIMARY KEY, name VARCHAR, description VARCHAR,  totalNumber INT);
CREATE TABLE news(id SERIAL PRIMARY KEY, general VARCHAR, department VARCHAR);
CREATE TABLE users(id SERIAL PRIMARY KEY, name VARCHAR, position VARCHAR, role VARCHAR, email VARCHAR, badgeNo INT, department VARCHAR);
CREATE TABLE departments_news(id SERIAL PRIMARY KEY, newsId INTEGER, departmentId INTEGER);
CREATE DATABASE news_portal_test WITH TEMPLATE news_portal;
