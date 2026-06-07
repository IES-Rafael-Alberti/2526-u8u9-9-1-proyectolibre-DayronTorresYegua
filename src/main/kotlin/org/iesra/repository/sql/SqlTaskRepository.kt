package org.iesra.repository.sql

import org.iesra.model.Priority
import org.iesra.model.Task
import org.iesra.model.TaskStatus
import org.iesra.repository.TaskRepository
import java.sql.Connection
import java.sql.Statement
import java.sql.Types

class SqlTaskRepository(
    private val connectionProvider: () -> Connection = { H2Database.openConnection() },
) : TaskRepository {

    override fun create(task: Task): Task {
        connectionProvider().use { conn ->
            Schema.ensureCreated(conn)
            val assigneeId = resolveAssignee(task.assignee, conn)
            val sql = """
                INSERT INTO tasks (title, description, status, priority, assignee_id)
                VALUES (?, ?, ?, ?, ?)
            """.trimIndent()
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { ps ->
                ps.setString(1, task.title)
                ps.setString(2, task.description)
                ps.setString(3, task.status.name)
                ps.setString(4, task.priority.name)
                if (assigneeId != null) ps.setLong(5, assigneeId) else ps.setNull(5, Types.BIGINT)
                ps.executeUpdate()
                ps.generatedKeys.use { keys ->
                    keys.next()
                    return task.copy(id = keys.getLong(1))
                }
            }
        }
    }

    override fun findById(id: Long): Task? = connectionProvider().use { conn ->
        Schema.ensureCreated(conn)
        val sql = """
            SELECT t.id, t.title, t.description, t.status, t.priority, a.name AS assignee
            FROM tasks t LEFT JOIN assignees a ON t.assignee_id = a.id
            WHERE t.id = ?
        """.trimIndent()
        conn.prepareStatement(sql).use { ps ->
            ps.setLong(1, id)
            ps.executeQuery().use { rs ->
                if (rs.next()) mapRow(rs) else null
            }
        }
    }

    override fun findAll(): List<Task> {
        connectionProvider().use { conn ->
            Schema.ensureCreated(conn)
            val sql = """
                SELECT t.id, t.title, t.description, t.status, t.priority, a.name AS assignee
                FROM tasks t LEFT JOIN assignees a ON t.assignee_id = a.id
                ORDER BY t.id
            """.trimIndent()
            conn.prepareStatement(sql).use { ps ->
                ps.executeQuery().use { rs ->
                    val out = mutableListOf<Task>()
                    while (rs.next()) out += mapRow(rs)
                    return out
                }
            }
        }
    }

    override fun update(task: Task): Task? {
        connectionProvider().use { conn ->
            Schema.ensureCreated(conn)
            val assigneeId = resolveAssignee(task.assignee, conn)
            val sql = """
                UPDATE tasks SET title=?, description=?, status=?, priority=?, assignee_id=?
                WHERE id=?
            """.trimIndent()
            conn.prepareStatement(sql).use { ps ->
                ps.setString(1, task.title)
                ps.setString(2, task.description)
                ps.setString(3, task.status.name)
                ps.setString(4, task.priority.name)
                if (assigneeId != null) ps.setLong(5, assigneeId) else ps.setNull(5, Types.BIGINT)
                ps.setLong(6, task.id)
                return if (ps.executeUpdate() > 0) task else null
            }
        }
    }

    override fun deleteById(id: Long): Boolean {
        connectionProvider().use { conn ->
            Schema.ensureCreated(conn)
            val sql = "DELETE FROM tasks WHERE id = ?"
            conn.prepareStatement(sql).use { ps ->
                ps.setLong(1, id)
                return ps.executeUpdate() > 0
            }
        }
    }

    override fun deleteAll() {
        connectionProvider().use { conn ->
            Schema.ensureCreated(conn)
            conn.createStatement().use { st ->
                st.execute("DELETE FROM tasks")
                st.execute("DELETE FROM assignees")
            }
        }
    }

    private fun resolveAssignee(name: String?, conn: Connection): Long? {
        if (name == null) return null
        val selectSql = "SELECT id FROM assignees WHERE name = ?"
        conn.prepareStatement(selectSql).use { ps ->
            ps.setString(1, name)
            ps.executeQuery().use { rs ->
                if (rs.next()) return rs.getLong("id")
            }
        }
        val insertSql = "INSERT INTO assignees (name) VALUES (?)"
        conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS).use { ps ->
            ps.setString(1, name)
            ps.executeUpdate()
            ps.generatedKeys.use { keys ->
                keys.next()
                return keys.getLong(1)
            }
        }
    }

    private fun mapRow(rs: java.sql.ResultSet): Task = Task(
        id = rs.getLong("id"),
        title = rs.getString("title"),
        description = rs.getString("description"),
        status = TaskStatus.valueOf(rs.getString("status")),
        priority = Priority.valueOf(rs.getString("priority")),
        assignee = rs.getString("assignee"),
    )
}
