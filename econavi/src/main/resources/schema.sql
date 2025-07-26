-- Test용 DB 및 User 생성 쿼리
-- CREATE DATABASE econavi_test;
-- CREATE USER 'econavi_test'@'%' IDENTIFIED BY 'econaviTest';
-- GRANT ALL PRIVILEGES ON econavi_test.* TO 'econavi_test'@'%';
-- USE econavi_test;

CREATE TABLE IF NOT EXISTS members (
    id
    BIGINT
    NOT
    NULL
    AUTO_INCREMENT
    PRIMARY
    KEY
);