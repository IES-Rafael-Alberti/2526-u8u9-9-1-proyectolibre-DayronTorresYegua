package org.iesra.service

import org.iesra.model.Task

object NoOpTaskHistoryLogger : TaskHistoryLogger {
    override fun logCreated(task: Task) {}
    override fun logUpdated(task: Task) {}
    override fun logDeleted(task: Task) {}
    override fun logStatusChanged(task: Task) {}
}
