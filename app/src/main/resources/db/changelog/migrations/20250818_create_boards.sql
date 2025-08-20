--liquibase formatted sql
--changeset pedro:202508181200
--comment: Create boards table

CREATE TABLE BOARDS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;


--rollback DROP TABLE BOARDS
