package deploy.blocking

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.core.VertxOptions

/**
 * Regra de ouro do Vert.x: Não bloqueie o barramento de eventos
 *
 * Nesse exemplo iremos fazer a implantação de dois Verticles
 * Por padrão o Vert.x cria 2 * número de cores event loops.
 *
 * Logo, se um dos verticles bloquear o loop de eventos o outro não seria afetado.
 *
 * No entanto, conforme o número de Verticles cresce na aplicação o fato de um
 * deles bloquear o barramento de eventos pode impactar nos outros que estão executando no mesmo Loop.
 *
 * Podemos alterar o número de loops de eventos para 1 para simular esse comportamento.
 *
 * Após 4 segundos o primeiro Verticle irá bloquear o barramento de eventos e
 * impedir a execução do segundo
 */
fun main(args : Array<String>) {
    val vertx = Vertx.vertx(VertxOptions(
        eventLoopPoolSize = 1
    ))

    vertx.deployVerticle(GoldenRuleBreaker()){
        if (it.succeeded()) {
            val deploymentID = it.result()

            /*
             * Vamos tentar fazer o undeploy do Verticle daqui 6 segundos
             * Como ele estará num estado de bloqueio, o Vert.x só faz o undeploy após a tarefa bloqueante terminar
             * Portanto ele nunca conseguirá fazer o undeploy do Verticle.....
             */
            vertx.setTimer(6000, {
                vertx.undeploy(deploymentID)
            })
        } else {
            println(it.cause())
        }
    }

    vertx.deployVerticle(PlainOldVerticle())
}

class GoldenRuleBreaker : AbstractVerticle() {
    override fun start() {
        //I'll fuck the event loop the next second

        println("GoldenRuleBreaker in Thread: ${Thread.currentThread().name}")

        vertx.setTimer(4000, {
            println("Messing with the event loop now...")
            while(true) {
              // I'll block 4ever
            }
        })
    }
}

class PlainOldVerticle : AbstractVerticle() {
    private var counter = 0

    override fun start() {
        println("PlainOldVerticle in Thread: ${Thread.currentThread().name}")
        schedulePrintCounterEverySecond()

    }

    fun schedulePrintCounterEverySecond() {
        println("Executing in ${Thread.currentThread().name}: counter (${counter})")
        counter += 1
        vertx.setTimer(1000, {schedulePrintCounterEverySecond()})
    }
}
