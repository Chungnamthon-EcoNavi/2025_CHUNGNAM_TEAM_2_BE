-- Test용 DB 및 User 생성 쿼리
-- CREATE DATABASE econavi_test;
-- CREATE USER 'econavi_test'@'%' IDENTIFIED BY 'econaviTest';
-- GRANT ALL PRIVILEGES ON econavi_test.* TO 'econavi_test'@'%';
-- USE econavi_test;

CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role ENUM('USER', 'STAFF') NOT NULL,
    photo_url VARCHAR(512),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
