package application.database

import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.awaitResult
import io.vertx.serviceproxy.ServiceBinder


const val DB_SERVICE_ADDRESS = "bravo.db"

class DatabaseVerticle : CoroutineVerticle() {

    private val dbConfigObject = JsonObject()
        .put("url", "jdbc:hsqldb:file:db/bravo")
        .put("driver_class", "org.hsqldb.jdbcDriver")


    override suspend fun start() {
        val dbClient = JDBCClient.createShared(vertx, dbConfigObject)
        val dbService = awaitResult<DatabaseService> { DatabaseService.create(dbClient, it) }
        ServiceBinder(vertx)
            .setAddress(DB_SERVICE_ADDRESS)
            .register(DatabaseService::class.java, dbService)

    }
}


