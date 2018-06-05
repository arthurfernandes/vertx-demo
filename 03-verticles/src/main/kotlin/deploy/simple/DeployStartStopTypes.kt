package deploy.simple

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx

/**
 * Esse exemplo demonstra as duas formas de utilizar o start/stop de um Verticle
 *
 * Ao executar override fun start() ou override fun stop(),
 * após o retorno de uma dessas funções o Verticle é considerado deployado.
 * Utilize esses métodos quando o seu deploy/undeploy for simples
 *
 * Quando ocorrer inicialização de recursos ou tarefas demoradas prefira o outro modo:
 * override fun start(startFuture : Future<Void>)
 * override fun stop(startFuture : Future<Void>)
 *
 * O verticle só será considerado pronto quando você
 * explicitamente executar startFuture.complete()
 */
fun main(args : Array<String>) {
    val vertx = Vertx.vertx()


    vertx.deployVerticle(SimpleStartVerticle()) {
        val deploymentID = it.result()
        println("Simple Verticle Deployment ID: ${deploymentID}")

        vertx.undeploy(deploymentID) {
            println("The deploy and undeploy happens automatically for the simple\n")
        }
    }

    vertx.deployVerticle(AsyncStartVerticle()) {
        val deploymentID = it.result()
        println("Async Verticle Deployment ID: ${deploymentID}")

        vertx.undeploy(deploymentID) {
            println("Undeploy Async Verticle Ready")
        }
    }
}

/**
 * Use esse método quando a implantação for simples
 */
class SimpleStartVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploying Simple Start")
    }

    override fun stop() {
        println("Undeploying Simple Start")
    }
}

/**
 * Use esse método quando a implantação for complexa e
 * necessitar abrir/fechar outros recursos (arquivos, sockets, servidores, Verticles)
 */
class AsyncStartVerticle : AbstractVerticle() {
    override fun start(startFuture : Future<Void>) {
        println("Deploying Async: will be ready in 5 seconds")
        vertx.setTimer(5000, {
            startFuture.complete()
        })
    }

    override fun stop(stopFuture: Future<Void>) {
        println("Undeploy Async: will be ready in 5 seconds")

        vertx.setTimer(5000, {
            stopFuture.complete()
        })
    }
}