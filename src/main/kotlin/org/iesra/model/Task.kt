package org.iesra.model

/**
 * Entidad principal del dominio que representa una tarea.
 *
 * @property id Identificador único (0 si aún no está persistida).
 * @property title Título de la tarea (2-100 caracteres, validado).
 * @property description Descripción opcional.
 * @property status Estado de la tarea (PENDING o COMPLETED).
 * @property priority Prioridad (LOW, MEDIUM, HIGH).
 * @property assignee Persona encargada opcional (2-60 caracteres).
 */
data class Task(
    val id: Long,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val priority: Priority,
    val assignee: String?,
)
