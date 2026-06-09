package org.iesra.repository.mongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.iesra.util.AppConfig

object MongoClientProvider {
    fun createClient(): MongoClient = MongoClients.create(AppConfig.mongoUri)
}
