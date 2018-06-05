package deploy.blocking

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.core.DeploymentOptions
import io.vertx.kotlin.core.VertxOptions

/**
 * Nesse exemplo iremos implantar múltiplos Worker Verticles
 *
 * Os workers competem por Threads no Worker Pool
 *
 * Para simular esse comportamento reduzir o Pool para apenas 1 Thread,
 * e cada um deles bloqueia por 1 segundo a Thread para executar uma tarefa,
 * a cada 6 segundos
 *
 */
fun main(args : Array<String>) {
    val vertx = Vertx.vertx(VertxOptions(
        workerPoolSize = 1
    ))

    vertx.deployVerticle(AWorkerVerticle::class.java, DeploymentOptions(
        worker = true,
        instances = 4
    ))

}

/**
 * Bloqueia a Thread por 1 segundo, a cada 6 segundos
 *
 * Repare que não estamos utilizando a função vertx.setPeriodic, pois
 * (pelos mesmos motivos do setInterval do Javascript) as chamadas podem
 * se acumular numa fila
 */
class AWorkerVerticle : AbstractVerticle() {
    override fun start() {
        blockThreadEvery6Seconds()
    }

    fun blockThreadEvery6Seconds() {
        println(""" I'm running in: ${Thread.currentThread().name}.
                    I'll block the thread for 2 sec =/
                """.trimIndent())

        //Blocking the thread
        Thread.sleep(2000)

        vertx.setTimer(6000, {
            blockThreadEvery6Seconds()
        })
    }
}