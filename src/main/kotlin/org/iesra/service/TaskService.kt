package org.iesra.service

import org.iesra.exception.NotFoundException
import org.iesra.model.Priority
import org.iesra.model.Task
import org.iesra.model.TaskStatus
import org.iesra.repository.TaskRepository
import org.iesra.validator.TaskValidator

/**
 * Servicio principal de la aplicación.
 * Contiene la lógica de negocio para operaciones CRUD sobre tareas,
 * aplicando validaciones y registrando historial de operaciones.
 *
 * @property repository Repositorio de persistencia (H2, memoria, etc.).
 * @property historyLogger Logger opcional para historial de operaciones.
 */
class TaskService(
    private val repository: TaskRepository,
    private val historyLogger: TaskHistoryLogger = NoOpTaskHistoryLogger,
) {
    /**
     * Crea una nueva tarea con validación de título y asignado.
     * @param title Título de la tarea (obligatorio, 2-100 caracteres).
     * @param description Descripción opcional.
     * @param priority Prioridad de la tarea (por defecto MEDIUM).
     * @param assignee Persona encargada opcional.
     * @return La tarea creada con su id asignado.
     */
    fun createTask(
        title: String,
        description: String?,
        priority: Priority = Priority.MEDIUM,
        assignee: String? = null,
    ): Task {
        val normalizedTitle = TaskValidator.normalizeTitle(title)
        val normalizedDescription = TaskValidator.normalizeDescription(description)
        val normalizedAssignee = TaskValidator.normalizeAssignee(assignee)
        val created = repository.create(
            Task(
                id = 0,
                title = normalizedTitle,
                description = normalizedDescription,
                status = TaskStatus.PENDING,
                priority = priority,
                assignee = normalizedAssignee,
            ),
        )
        historyLogger.logCreated(created)
        return created
    }

    /**
     * Lista tareas, opcionalmente filtradas por estado.
     * @param status Estado por el que filtrar (null para todas).
     */
    fun listTasks(status: TaskStatus? = null): List<Task> {
        val all = repository.findAll()
        return if (status == null) all else all.filter { it.status == status }
    }

    /**
     * Obtiene una tarea por su id.
     * @throws NotFoundException si no existe la tarea.
     */
    fun getTask(id: Long): Task = repository.findById(id)
        ?: throw NotFoundException("No existe la tarea con id=$id")

    /**
     * Actualiza los datos de una tarea existente.
     * @throws NotFoundException si no existe la tarea.
     */
    fun updateTask(
        id: Long,
        newTitle: String,
        newDescription: String?,
        newPriority: Priority,
        newAssignee: String?,
    ): Task {
        val existing = getTask(id)
        val normalizedTitle = TaskValidator.normalizeTitle(newTitle)
        val normalizedDescription = TaskValidator.normalizeDescription(newDescription)
        val normalizedAssignee = TaskValidator.normalizeAssignee(newAssignee)
        val updated = existing.copy(
            title = normalizedTitle,
            description = normalizedDescription,
            priority = newPriority,
            assignee = normalizedAssignee,
        )
        val result = repository.update(updated)
            ?: throw NotFoundException("No existe la tarea con id=$id")
        historyLogger.logUpdated(result)
        return result
    }

    /**
     * Elimina una tarea por su id.
     * @throws NotFoundException si no existe la tarea.
     */
    fun deleteTask(id: Long) {
        val task = getTask(id)
        repository.deleteById(id)
        historyLogger.logDeleted(task)
    }

    /**
     * Elimina todas las tareas.
     */
    fun deleteAll() {
        val tasks = repository.findAll()
        repository.deleteAll()
        tasks.forEach { historyLogger.logDeleted(it) }
    }

    /**
     * Cambia el estado de una tarea.
     * @throws NotFoundException si no existe la tarea.
     */
    fun setStatus(id: Long, status: TaskStatus): Task {
        val existing = getTask(id)
        val updated = existing.copy(status = status)
        val result = repository.update(updated)
            ?: throw NotFoundException("No existe la tarea con id=$id")
        historyLogger.logStatusChanged(result)
        return result
    }

    /** Marca una tarea como completada. */
    fun markCompleted(id: Long): Task = setStatus(id, TaskStatus.COMPLETED)

    /** Marca una tarea como pendiente. */
    fun markPending(id: Long): Task = setStatus(id, TaskStatus.PENDING)
}
