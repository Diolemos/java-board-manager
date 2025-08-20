--liquibase formatted sql
--changeset pedro:202508182346
--comment: create board columns table

CREATE TABLE BOARDS_COLUMNS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `order` INT NOT NULL,
    kind VARCHAR(50) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT fk_board FOREIGN KEY (board_id) REFERENCES BOARDS(id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS_COLUMNS
