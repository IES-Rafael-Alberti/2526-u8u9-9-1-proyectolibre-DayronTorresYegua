package org.iesra.repository.sql

import java.sql.Connection
import java.sql.DriverManager
import org.iesra.util.AppConfig

object H2Database {
    fun openConnection(): Connection {
        val url = AppConfig.h2Url
        val user = AppConfig.h2User
        val pass = AppConfig.h2Password
        return if (user.isBlank() && pass.isBlank()) {
            DriverManager.getConnection(url)
        } else {
            DriverManager.getConnection(url, user, pass)
        }
    }
}
