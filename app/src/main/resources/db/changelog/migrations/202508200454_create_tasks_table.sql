--liquibase formatted sql
--changeset pedro:202508200454
--comment: Create TASKS table

CREATE TABLE TASKS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NULL,
    column_id BIGINT NOT NULL,
    CONSTRAINT tasks__columns_fk FOREIGN KEY (column_id)
        REFERENCES BOARDS_COLUMNS(id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE TASKS
