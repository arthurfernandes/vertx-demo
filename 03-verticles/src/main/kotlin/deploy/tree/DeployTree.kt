package deploy.tree

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.core.DeploymentOptions

/**
 * Esse exemplo demonstra o que ocorre com o deploy/undeploy de Múltiplos Verticles
 *
 * A implanta B (2 instâncias) e C
 * B implanta D e E (4 instâncias)
 *
 * Quando fizer Undeploy de A ===> faz undeploy de B (todas as instâncias) e C
 * Quando fizer Undeploy de B ===> faz undeploy de D e E (todas as instâncias)
 *
 */
fun main(args : Array<String>) {
    val vertx = Vertx.vertx()

    vertx.deployVerticle(AVerticle(), {
        val deploymentID = it.result()

        println("""

            ##### NOW WE WAIT 5s

            """.trimIndent())

        //Undeploy A in 5 seconds
        vertx.setTimer(5000) {
            vertx.undeploy(deploymentID)
        }
    })
}

class AVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploying A")

        vertx.deployVerticle(BVerticle::class.java, DeploymentOptions(
                instances = 2
        ))

        vertx.deployVerticle(CVerticle())
    }

    override fun stop() {
        println("Undeploy A Verticle in Thread: ${Thread.currentThread().name}")
    }
}

class BVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploying B verticle in Thread: ${Thread.currentThread().name}")

        vertx.deployVerticle(DVerticle())
        vertx.deployVerticle(EVerticle::class.java, DeploymentOptions(
            instances = 4
        ))
    }

    override fun stop() {
        println("Undeploy B Verticle in Thread: ${Thread.currentThread().name}")
    }
}

class CVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploying C Verticle in Thread: ${Thread.currentThread().name}")
    }

    override fun stop() {
        println("Undeploy C Verticle in Thread: ${Thread.currentThread().name}")
    }
}

class DVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploying D Verticle in Thread: ${Thread.currentThread().name}")
    }

    override fun stop() {
        println("Undeploy D Verticle in Thread: ${Thread.currentThread().name}")
    }
}

class EVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploying E Verticle in Thread: ${Thread.currentThread().name}")
    }

    override fun stop() {
        println("Undeploy E Verticle in Thread: ${Thread.currentThread().name}")
    }
}