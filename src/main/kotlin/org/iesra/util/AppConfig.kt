package org.iesra.util

/**
 * Configuracion centralizada via variables de entorno.
 *
 * Deja los valores por defecto y solo define usuario/password/uri en tu entorno.
 */
object AppConfig {
    val h2Url: String = env("TASK_H2_URL", "jdbc:h2:./data/task_manager")
    val h2User: String = env("TASK_H2_USER", "")
    val h2Password: String = env("TASK_H2_PASSWORD", "")

    val mongoUri: String = env("MONGODB_URI", "mongodb+srv://dtorresy_db_user:mongodb@tallermongo.e1g2qpy.mongodb.net/")
    val mongoDatabase: String = env("MONGODB_DATABASE", "task_manager")
    val mongoErrorsCollection: String = env("TASK_MONGO_ERRORS_COLLECTION", "file_processing_errors")

    private fun env(name: String, default: String): String = System.getenv(name)?.takeIf { it.isNotBlank() } ?: default
}
