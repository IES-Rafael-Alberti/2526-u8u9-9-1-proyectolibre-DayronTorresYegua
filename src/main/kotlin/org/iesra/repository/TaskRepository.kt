package org.iesra.repository

import org.iesra.model.Task

/**
 * Interfaz del repositorio de tareas.
 * Define el contrato para las operaciones CRUD independientemente
 * de la tecnología de persistencia subyacente.
 */
interface TaskRepository {
    /** Crea una nueva tarea y devuelve la tarea con su id asignado. */
    fun create(task: Task): Task

    /** Busca una tarea por su id. Devuelve null si no existe. */
    fun findById(id: Long): Task?

    /** Devuelve todas las tareas. */
    fun findAll(): List<Task>

    /** Actualiza una tarea existente. Devuelve null si el id no existe. */
    fun update(task: Task): Task?

    /** Elimina una tarea por su id. Devuelve true si se eliminó. */
    fun deleteById(id: Long): Boolean

    /** Elimina todas las tareas y sus asignados. */
    fun deleteAll()
}
