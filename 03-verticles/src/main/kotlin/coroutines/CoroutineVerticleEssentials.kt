package coroutines

import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.future.toCompletableFuture
import java.util.concurrent.TimeUnit

/**
 * Nos exemplos abaixo iremos mostrar a utilização de algumas
 * funções da biblioteca de corotinas do Kotlin como delay, launch, runBlocking, async
 * junto com o Vert.x
 *
 * É importante notar o que acontece quando não executamos no contexto das corotinas
 * (passando o vertx.dispatcher()), o que pode nos fazer violar a thread safety da nossa aplicacao
 *
 * As corotinas só podem ser utilizadas em funções com anotação suspend.
 * Como não podemos fazer isso com a função main (o suspend faz parte da assinatura),
 * temos a opção abaixo com o runBlocking:
 *
 * Ao utilizar o Vert.x lembre-se de não utilizar runBlocking em Threads do Loop de Eventos
 */
fun main(args : Array<String>) = runBlocking {

    val vertx = Vertx.vertx()

    val deploymentID = awaitResult<String> {
        vertx.deployVerticle(CoroutineVerticleEssentials(), it)
    }

    println("Deploy finalizado de ${deploymentID}")

    //Como esse verticle não faz mais nada vamos fechar o vertx agora
    vertx.close()
}


class CoroutineVerticleEssentials : CoroutineVerticle() {
    override suspend fun start() {
        println("""
            Executando em ${Thread.currentThread().name}
            Vamos esperar 5 segundos para comecar a executar o codigo
            Caso a thread do loop de eventos seja bloqueada por mais de 2 segundos aparece warning
            Apareceu algum warning?
            """.trimIndent())

        delay(5, TimeUnit.SECONDS)

        println("Bom!")
        println("Agora vamos contar até 10 com o launch")

        //Vamos lançar uma tarefa que conta até 10
        val job = launch(vertx.dispatcher()) {
            for (i in 0..9) {
                println("Contando: ${i+1}")
                //Espera 1 segundo
                delay(1000)
            }
        }

        //Suspender a execução até que o trabalho termine
        job.join()

        println("Agora vamos executar duas tarefas paralelas com async")
        println("Uma delas esqueceu de passsar o contexto e além do mais bloqueou...")

        //Podemos executar tarefas em paralelo com async
        //O que retorna é um Deferred (parecido com Promise)
        val defer1 = async(vertx.dispatcher()) {
            println("Executando tarefa 1 em ${Thread.currentThread().name}")
            delay(2000)
            println("Essa thread não ficou bloqueada ${Thread.currentThread().name}")
        }

        //Executando de propósito sem o vertx.dispatcher()
        //Observe a Thread que ele utiliza para executar
        //Tente colocar vertx.dispatcher e observe o valor retornado
        val defer2 = async {
            println("Executando tarefa 2 em ${Thread.currentThread().name}")

            //Como não estamos executando no loop de eventos vou parar a Thread por 5 segundos
            //Reparem que o Vert.x não reclama
            Thread.sleep(5000)
            println("Desbloqueou a thread ${Thread.currentThread().name}")
        }

        //Vamos esperar a execução dos 2 terminarem
        defer1.await();
        defer2.await();

        delay(1000)

        //Não execute run blocking na thread do loop de eventos
        println("O Vert.x vai reclamar que vamos bloquear o loop de eventos agora")

        runBlocking {
            delay(5000)
        }

        println("Vamos rodar uma tarefa bloqueante e o vert.x não vai reclamar:")
        //Se você quer rodar código bloqueante o Vertx disponibiliza o awaitBlocking:
        val result = awaitBlocking<String>{
            println("Começando")
            Thread.sleep(5000)
            println("Terminado após 5 segundos")
            //aqui você passa o resultado da operação
            "Foi um exemplo longo mas acabou. Time to relax..."
        }

        println(result)
    }
}