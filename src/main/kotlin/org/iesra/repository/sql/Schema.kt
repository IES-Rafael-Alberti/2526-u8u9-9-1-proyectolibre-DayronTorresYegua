package org.iesra.repository.sql

import java.sql.Connection
import java.sql.Statement

object Schema {
    private val ddl = """
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
    """.trimIndent()

    fun ensureCreated(conn: Connection) {
        conn.createStatement().use { st ->
            st.execute(ddl)
        }
    }
}
