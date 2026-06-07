-- H2 schema for Task Manager
-- Database: task_manager (created by app config)

CREATE TABLE IF NOT EXISTS assignees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    status VARCHAR(20) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    assignee_id BIGINT,
    FOREIGN KEY (assignee_id) REFERENCES assignees(id)
);
