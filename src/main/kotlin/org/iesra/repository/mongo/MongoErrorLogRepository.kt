package org.iesra.repository.mongo

import com.mongodb.client.MongoClient
import org.bson.Document
import org.iesra.util.AppConfig
import org.slf4j.LoggerFactory
import java.time.Instant

class MongoErrorLogRepository(
    private val client: MongoClient = MongoClientProvider.createClient(),
) {
    private val logger = LoggerFactory.getLogger(MongoErrorLogRepository::class.java)

    fun logFileProcessingError(
        sourceFile: String,
        lineNumber: Int,
        rawLine: String,
        message: String,
    ) {
        val db = client.getDatabase(AppConfig.mongoDatabase)
        val coll = db.getCollection(AppConfig.mongoErrorsCollection)

        val doc = Document()
            .append("ts", Instant.now().toString())
            .append("sourceFile", sourceFile)
            .append("lineNumber", lineNumber)
            .append("rawLine", rawLine)
            .append("message", message)

        try {
            coll.insertOne(doc)
        } catch (e: Exception) {
            logger.warn("No se pudo guardar error en MongoDB: {}", e.message)
        }
    }
}
