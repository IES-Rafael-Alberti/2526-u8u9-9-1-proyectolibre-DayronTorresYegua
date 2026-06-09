package org.iesra.validator

import org.iesra.exception.ValidationException

/**
 * Valida y normaliza los campos de una tarea.
 * Utiliza expresiones regulares para garantizar el formato correcto.
 */
object TaskValidator {
    private val titleRegex = Regex("^[\\p{L}0-9][\\p{L}0-9 _\\-]{1,98}[\\p{L}0-9]$")
    private val assigneeRegex = Regex("^[\\p{L}][\\p{L} '.\\-]{1,58}[\\p{L}]$")

    /**
     * Valida y normaliza el título de una tarea.
     * @throws ValidationException si el título está vacío o no cumple el formato.
     */
    fun normalizeTitle(title: String): String {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) throw ValidationException("El titulo no puede estar vacio")
        if (!titleRegex.matches(trimmed)) {
            throw ValidationException(
                "El titulo no es valido (2-100 caracteres, letras/numeros/espacios/_/-; sin espacios al inicio/fin)",
            )
        }
        return trimmed
    }

    /** Normaliza la descripción (trim si no es null). */
    fun normalizeDescription(description: String?): String? = description?.trim()?.takeIf { it.isNotEmpty() }

    /**
     * Valida y normaliza la persona encargada.
     * @throws ValidationException si no cumple el formato.
     */
    fun normalizeAssignee(assignee: String?): String? {
        val normalized = assignee?.trim()?.takeIf { it.isNotEmpty() } ?: return null
        if (!assigneeRegex.matches(normalized)) {
            throw ValidationException("Persona encargada no valida (2-60 caracteres, letras y separadores comunes)")
        }
        return normalized
    }
}
