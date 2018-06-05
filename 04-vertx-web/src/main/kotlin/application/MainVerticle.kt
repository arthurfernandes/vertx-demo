package application

import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.awaitResult

fun main(args : Array<String>) {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(MainVerticle())
}

/**
 * Ponto de entrada da aplicação
 * Faz a implantação dos outros Verticles
 */
class MainVerticle : CoroutineVerticle() {
    override suspend fun start() {
        awaitResult<String> { vertx.deployVerticle(HttpVerticle(), it) }
        awaitResult<String> { vertx.deployVerticle(LocationVerticle(), it)}
    }
}