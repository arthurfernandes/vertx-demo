package application.main

import application.database.DatabaseVerticle
import application.resteasy.HttpVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.CoroutineVerticle

fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(MainVerticle())
}

class MainVerticle : CoroutineVerticle(){
    override suspend fun start() {
        vertx.deployVerticle(DatabaseVerticle())
        vertx.deployVerticle(HttpVerticle())
    }
}

