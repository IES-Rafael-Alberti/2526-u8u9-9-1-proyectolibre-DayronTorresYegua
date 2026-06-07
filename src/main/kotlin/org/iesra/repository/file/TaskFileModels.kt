package org.iesra.repository.file

data class ProcessingError(
    val lineNumber: Int,
    val rawLine: String,
    val message: String,
)

data class TaskRecord(
    val title: String,
    val description: String?,
    val status: org.iesra.model.TaskStatus,
    val priority: org.iesra.model.Priority,
    val assignee: String?,
)

data class ProcessingSummary(
    val totalLines: Int,
    val validTasks: Int,
    val invalidLines: Int,
    val errors: List<ProcessingError> = emptyList(),
)
