package org.iesra.app

import org.iesra.exception.NotFoundException
import org.iesra.exception.ValidationException
import org.iesra.model.Priority
import org.iesra.model.Task
import org.iesra.model.TaskStatus
import org.iesra.repository.file.TaskFileExporter
import org.iesra.service.TaskService
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.createDirectories

/**
 * Interfaz de usuario por consola.
 * Muestra un menú interactivo y gestiona la entrada/salida con el usuario.
 *
 * @property service Servicio de tareas del que dependen todas las operaciones.
 */
class ConsoleUi(
    private val service: TaskService,
) {
    fun run() {
        println("Task Manager (H2 + MongoDB + Ficheros)")

        var running = true
        while (running) {
            printMenu()
            val option = readOption() ?: return
            running = handleOption(option)
        }
    }

    private fun handleOption(option: String): Boolean {
        return when (option) {
            "1" -> safeUi { create() }.let { true }
            "2" -> safeUi { listAll() }.let { true }
            "3" -> safeUi { listByStatus(TaskStatus.PENDING) }.let { true }
            "4" -> safeUi { listByStatus(TaskStatus.COMPLETED) }.let { true }
            "5" -> safeUi { update() }.let { true }
            "6" -> safeUi { delete() }.let { true }
            "7" -> safeUi { mark(TaskStatus.COMPLETED) }.let { true }
            "8" -> safeUi { mark(TaskStatus.PENDING) }.let { true }
            "9" -> safeUi { exportToFile() }.let { true }
            "0" -> {
                println("Saliendo...")
                false
            }

            else -> {
                println("Opcion no valida")
                true
            }
        }
    }

    private fun printMenu() {
        println()
        println("1. Crear tarea")
        println("2. Listar tareas (todas)")
        println("3. Listar tareas (pendientes)")
        println("4. Listar tareas (completadas)")
        println("5. Actualizar tarea")
        println("6. Eliminar tarea")
        println("7. Marcar como completada")
        println("8. Marcar como pendiente")
        println("9. Exportar tareas a fichero (CSV)")
        println("0. Salir")
        print("> ")
    }

    private fun create() {
        val title = promptNonEmpty("Titulo") ?: return
        val description = promptOptional("Descripcion (opcional)")
        val priority = promptPriority() ?: return
        val assignee = promptOptional("Persona encargada (opcional)")
        val created = service.createTask(title, description, priority, assignee)
        println("Creada: ${formatTask(created)}")
    }

    private fun listAll() {
        val tasks = service.listTasks()
        printTasks(tasks)
    }

    private fun listByStatus(status: TaskStatus) {
        val tasks = service.listTasks(status)
        printTasks(tasks)
    }

    private fun update() {
        val id = promptId() ?: return
        val title = promptNonEmpty("Nuevo titulo") ?: return
        val description = promptOptional("Nueva descripcion (opcional)")
        val priority = promptPriority() ?: return
        val assignee = promptOptional("Persona encargada (opcional)")
        val updated = service.updateTask(id, title, description, priority, assignee)
        println("Actualizada: ${formatTask(updated)}")
    }

    private fun delete() {
        println("1) Eliminar por ID  2) Eliminar todas")
        print("> ")
        val opt = readLineTrimmedOrNull()
        when (opt) {
            "1" -> {
                val id = promptId() ?: return
                service.deleteTask(id)
                println("Tarea eliminada (id=$id)")
            }
            "2" -> {
                service.deleteAll()
                println("Todas las tareas eliminadas")
            }
            else -> println("Opcion no valida")
        }
    }

    private fun mark(status: TaskStatus) {
        val id = promptId() ?: return
        val updated = service.setStatus(id, status)
        println("Actualizada: ${formatTask(updated)}")
    }

    private fun exportToFile() {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        val output = Path.of("data/export/tasks_export_$timestamp.csv")
        output.parent?.createDirectories()
        val tasks = service.listTasks()
        TaskFileExporter().export(tasks, output)
        println("Exportadas ${tasks.size} tareas a $output")
    }

    private fun promptId(): Long? {
        val raw = promptNonEmpty("Id") ?: return null
        val id = raw.toLongOrNull()
        if (id == null || id <= 0) {
            println("Id no valido")
            return null
        }
        return id
    }

    private fun promptNonEmpty(label: String): String? {
        print("$label: ")
        val line = readLineTrimmedOrNull() ?: return null
        if (line.isEmpty()) {
            println("Valor no valido")
            return null
        }
        return line
    }

    private fun promptOptional(label: String): String? {
        print("$label: ")
        return readLineTrimmedOrNull()?.takeIf { it.isNotEmpty() }
    }

    private fun promptPriority(): Priority? {
        println("Prioridad: 1) LOW  2) MEDIUM  3) HIGH")
        print("> ")
        val raw = readLineTrimmedOrNull() ?: return null
        return when (raw) {
            "1" -> Priority.LOW
            "2" -> Priority.MEDIUM
            "3" -> Priority.HIGH
            else -> {
                println("Prioridad no valida")
                null
            }
        }
    }

    private fun printTasks(tasks: List<Task>) {
        if (tasks.isEmpty()) {
            println("(Sin tareas)")
            return
        }
        tasks.forEach { println(formatTask(it)) }
    }

    private fun formatTask(task: Task): String {
        val status = if (task.status == TaskStatus.COMPLETED) "[X]" else "[ ]"
        val desc = task.description?.let { " - $it" }.orEmpty()
        val pr = "(${task.priority})"
        val who = task.assignee?.let { " @$it" }.orEmpty()
        return "[ID:${task.id}] $status $pr ${task.title}$desc$who"
    }

    private fun safeUi(block: () -> Unit) {
        try {
            block()
        } catch (e: ValidationException) {
            println("Error: ${e.message}")
        } catch (e: NotFoundException) {
            println("Error: ${e.message}")
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
        }
    }

    private fun readOption(): String? = readLineTrimmedOrNull()

    private fun readLineTrimmedOrNull(): String? = readlnOrNull()?.trim()
}
