CREATE DATABASE seminar;
CREATE USER 'waffle-spring'@'localhost' IDENTIFIED BY 'wafflestudio';
GRANT ALL PRIVILEGES ON seminar.* TO 'waffle-spring'@'localhost';