package deploy.blocking

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx

/**
 * Algumas vezes você irá executar código bloqueante dentro do seu Verticle
 * O código bloqueante pode ser uma tarefa intensiva em CPU ou uma API síncrona
 *
 * Você pode fazer isso utilizando o executeBlocking
 */
fun main(args : Array<String>) {
    val vertx = Vertx.vertx()

    vertx.deployVerticle(AVerticleThatEventuallyBlocks())
}

/**
 * Esse Verticle executa uma tarefa bloqueante a cada 2 segundos
 * A tarefa bloqueante dura cerca de 3 segundos
 *
 * Esse Verticle também executa uma tarefa não bloqueante a cada segundo
 *
 * Repare que as tarefas são executadas em Threads diferentes
 *
 */
class AVerticleThatEventuallyBlocks : AbstractVerticle() {

    var nonBlockingCounter = 0
    var blockingCounter = 0

    override fun start() {
        println("Deploying our verticle in: ${Thread.currentThread().name}")
        println("Am I in the event loop? ${context.isEventLoopContext}\n")

        scheduleBlockingTask()
        scheduleUnblockingTask()
    }

    fun scheduleBlockingTask() {
        vertx.executeBlocking<Int>({
            //Here we execute our blocking code!
            println("I block stuff, that's why I execute in: ${Thread.currentThread().name}")

            Thread.sleep(2000)

            vertx.setTimer(2000, {
                scheduleBlockingTask()
            })

            //The result of the blocking operation
            it.complete(blockingCounter)

            blockingCounter += 1

        }, {
            if (it.succeeded()) {
                println("Blocking operation result: ${it.result()}\n")
            } else {
                println(it.cause())
            }
        })
    }

    fun scheduleUnblockingTask() {
        vertx.setPeriodic(1000, {
            println("I don't block the event loop! ${Thread.currentThread().name}")
            println("Non Blocking operation result: ${nonBlockingCounter}\n")
            nonBlockingCounter += 1
        })
    }
}