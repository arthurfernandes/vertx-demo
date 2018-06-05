package demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx

fun main(args : Array<String>) {
    val vertx = Vertx.vertx()

    vertx.deployVerticle(HelloWorldVerticle())
}

/**
 * Um simples Hello World com o Vert.x
 *
 * Cria um servidor http na porta 8080
 *
 */
class HelloWorldVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploy HelloWorldVerticle")

        vertx.createHttpServer()
            .requestHandler({
                it.response()
                    .putHeader("Content-type", "text/html")
                    .end("Hello World from Vert.x with Kotlin!")
            })
            .listen(8080)
    }

    override fun stop() {
        println("Undeploy HelloWorldVerticle")
    }
}