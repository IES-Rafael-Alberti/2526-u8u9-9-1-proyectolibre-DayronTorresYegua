package org.iesra.service

import org.iesra.model.Task

interface TaskHistoryLogger {
    fun logCreated(task: Task)
    fun logUpdated(task: Task)
    fun logDeleted(task: Task)
    fun logStatusChanged(task: Task)
}
