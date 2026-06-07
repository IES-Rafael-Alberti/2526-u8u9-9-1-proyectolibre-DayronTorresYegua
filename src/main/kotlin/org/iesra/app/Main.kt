package org.iesra.app

import com.mongodb.client.MongoClients
import org.iesra.repository.file.TaskFileProcessor
import org.iesra.repository.mongo.MongoErrorLogRepository
import org.iesra.repository.mongo.MongoTaskHistoryRepository
import org.iesra.repository.sql.SqlTaskRepository
import org.iesra.service.TaskService
import org.iesra.util.AppConfig
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.nameWithoutExtension

private val log = LoggerFactory.getLogger("org.iesra.app.Main")

fun main() {
    val taskRepository = SqlTaskRepository()

    val mongoClient = MongoClients.create(AppConfig.mongoUri)
    val historyLogger = MongoTaskHistoryRepository(mongoClient)
    val errorLogRepository = MongoErrorLogRepository(mongoClient)

    val taskService = TaskService(
        repository = taskRepository,
        historyLogger = historyLogger,
    )

    val processor = TaskFileProcessor(taskService, errorLogRepository)

    autoImportCsvFiles(processor)

    ConsoleUi(taskService).run()
}

private fun autoImportCsvFiles(processor: TaskFileProcessor) {
    val dataDir = Path.of("data")
    if (!dataDir.exists()) return

    val csvFiles = dataDir.listDirectoryEntries("*.csv")
    if (csvFiles.isEmpty()) return

    println("Importando ficheros CSV desde data/ ...")
    csvFiles.forEach { csvFile ->
        val errorJsonOutput = Path.of("data/errors/${csvFile.nameWithoutExtension}_errors.json")
        try {
            val summary = processor.process(
                inputFile = csvFile,
                errorJsonOutput = errorJsonOutput,
            )
            println("  ${csvFile.name}: ${summary.validTasks} tareas cargadas, ${summary.invalidLines} errores")
        } catch (e: Exception) {
            log.warn("Error al procesar {}: {}", csvFile.name, e.message)
        }
    }
}
