package org.iesra.repository.mongo

import com.mongodb.client.MongoClient
import org.bson.Document
import org.iesra.model.Task
import org.iesra.service.TaskHistoryLogger
import org.iesra.util.AppConfig
import org.slf4j.LoggerFactory
import java.time.Instant

class MongoTaskHistoryRepository(
    private val client: MongoClient,
) : TaskHistoryLogger {

    private val logger = LoggerFactory.getLogger(MongoTaskHistoryRepository::class.java)
    private val db = client.getDatabase(AppConfig.mongoDatabase)
    private val coll = db.getCollection("task_history")

    override fun logCreated(task: Task) = log("CREATED", task)
    override fun logUpdated(task: Task) = log("UPDATED", task)
    override fun logDeleted(task: Task) = log("DELETED", task)
    override fun logStatusChanged(task: Task) = log("STATUS_CHANGED", task)

    private fun log(action: String, task: Task) {
        val doc = Document()
            .append("ts", Instant.now().toString())
            .append("action", action)
            .append("taskId", task.id)
            .append(
                "snapshot", Document()
                    .append("id", task.id)
                    .append("title", task.title)
                    .append("description", task.description)
                    .append("status", task.status.name)
                    .append("priority", task.priority.name)
                    .append("assignee", task.assignee),
            )
        try {
            coll.insertOne(doc)
        } catch (e: Exception) {
            logger.warn("No se pudo registrar historial en MongoDB: {}", e.message)
        }
    }
}
