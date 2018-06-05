package deploy.blocking

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.core.DeploymentOptions

fun main(args : Array<String>) {
    val vertx = Vertx.vertx()

    // Na hora de fazer a implantação você especifica nas opções de deploy que ele é um worker
    vertx.deployVerticle(SimpleWorkerVerticle(), DeploymentOptions(
        worker = true
    ))
}

/**
 * Um worker Verticle é declarado como qualquer Verticle
 */
class SimpleWorkerVerticle : AbstractVerticle() {
    override fun start() {
        println("Am I a worker verticle? ${!context.isEventLoopContext}")
        println("I'm running in thread: ${Thread.currentThread().name}")
    }

    override fun stop() {}
}