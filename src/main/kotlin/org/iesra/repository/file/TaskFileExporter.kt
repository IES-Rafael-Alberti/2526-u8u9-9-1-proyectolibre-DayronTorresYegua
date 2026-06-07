package org.iesra.repository.file

import java.nio.file.Path
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createDirectories
import org.iesra.model.Task

class TaskFileExporter {
    fun export(tasks: List<Task>, outputFile: Path) {
        outputFile.parent?.createDirectories()
        outputFile.bufferedWriter().use { w ->
            w.appendLine("title;description;status;priority;assignee")
            tasks.forEach { task ->
                val line = buildString {
                    append(task.title)
                    append(';')
                    append(task.description.orEmpty())
                    append(';')
                    append(task.status.name)
                    append(';')
                    append(task.priority.name)
                    append(';')
                    append(task.assignee.orEmpty())
                }
                w.appendLine(line)
            }
        }
    }
}
