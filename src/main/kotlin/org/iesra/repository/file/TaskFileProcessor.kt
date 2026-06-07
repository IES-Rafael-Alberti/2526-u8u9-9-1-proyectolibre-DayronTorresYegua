package org.iesra.repository.file

import java.nio.file.Path
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.name
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readLines
import kotlin.io.path.writeText
import org.iesra.model.Priority
import org.iesra.model.TaskStatus
import org.iesra.repository.mongo.MongoErrorLogRepository
import org.iesra.service.TaskService

class TaskFileProcessor(
    private val service: TaskService,
    private val mongoErrors: MongoErrorLogRepository,
) {
    fun process(
        inputFile: Path,
        validTasksOutput: Path? = null,
        errorJsonOutput: Path? = null,
        summaryOutput: Path? = null,
    ): ProcessingSummary {
        if (!inputFile.exists()) throw IllegalArgumentException("No existe el fichero: $inputFile")
        validTasksOutput?.parent?.createDirectories()
        errorJsonOutput?.parent?.createDirectories()
        summaryOutput?.parent?.createDirectories()

        val lines = inputFile.readLines()
        var valid = 0
        var invalid = 0
        val errors = mutableListOf<ProcessingError>()

        val validWriter = validTasksOutput?.bufferedWriter()
        try {
            lines.forEachIndexed { idx, raw ->
                val lineNumber = idx + 1
                val trimmed = raw.trim()
                if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEachIndexed

                val parsed = tryParse(trimmed)
                if (parsed == null) {
                    invalid++
                    val err = ProcessingError(lineNumber, raw, "Formato invalido: se esperaban 5 campos separados por ';'")
                    errors += err
                    mongoErrors.logFileProcessingError(inputFile.name, lineNumber, raw, err.message)
                    return@forEachIndexed
                }

                try {
                    val created = service.createTask(parsed.title, parsed.description, parsed.priority, parsed.assignee)
                    if (parsed.status == TaskStatus.COMPLETED) service.markCompleted(created.id)
                    valid++
                    validWriter?.appendLine(trimmed)
                } catch (e: Exception) {
                    invalid++
                    val msg = e.message ?: e.javaClass.simpleName
                    val err = ProcessingError(lineNumber, raw, msg)
                    errors += err
                    mongoErrors.logFileProcessingError(inputFile.name, lineNumber, raw, msg)
                }
            }
        } finally {
            validWriter?.close()
        }

        if (errors.isNotEmpty() && errorJsonOutput != null) {
            writeErrorJson(errors, inputFile.name, errorJsonOutput)
        }

        val summary = ProcessingSummary(totalLines = lines.size, validTasks = valid, invalidLines = invalid, errors = errors)
        summaryOutput?.bufferedWriter()?.use { w ->
            w.appendLine("totalLines=${summary.totalLines}")
            w.appendLine("validTasks=${summary.validTasks}")
            w.appendLine("invalidLines=${summary.invalidLines}")
        }
        return summary
    }

    private fun writeErrorJson(errors: List<ProcessingError>, sourceFileName: String, output: Path) {
        val json = buildString {
            appendLine("{")
            appendLine("  \"sourceFile\": \"${escapeJson(sourceFileName)}\",")
            appendLine("  \"errors\": [")
            errors.forEachIndexed { i, e ->
                appendLine("    {")
                appendLine("      \"lineNumber\": ${e.lineNumber},")
                appendLine("      \"rawLine\": \"${escapeJson(e.rawLine)}\",")
                appendLine("      \"message\": \"${escapeJson(e.message)}\"")
                append("    }")
                if (i < errors.lastIndex) appendLine(",") else appendLine()
            }
            appendLine("  ]")
            append("}")
        }
        output.writeText(json)
    }

    private fun tryParse(line: String): TaskRecord? {
        val parts = line.split(';')
        if (parts.size != 5) return null
        val title = parts[0].trim()
        val description = parts[1].trim().takeIf { it.isNotEmpty() }
        val status = parts[2].trim().uppercase().let { runCatching { TaskStatus.valueOf(it) }.getOrNull() } ?: return null
        val priority = parts[3].trim().uppercase().let { runCatching { Priority.valueOf(it) }.getOrNull() } ?: return null
        val assignee = parts[4].trim().takeIf { it.isNotEmpty() }
        return TaskRecord(title, description, status, priority, assignee)
    }

    private fun escapeJson(s: String): String = s
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")
}
