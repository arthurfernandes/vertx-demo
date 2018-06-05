package exceptions

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.awaitResult
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.withTimeout

/**
 * Esse exemplo demonstra em que pontos do código você pode pegar as exceções que não foram tratadas
 *
 * As seguintes situações são simuladas:
 *
 * Cenario 0) Erro no deploy do Verticle
 * Cenario 1) Não há tratamento de exceções. O erro nem mesmo é logado
 * Cenario 2) Há um tratamento de exceções genérico para o verticle
 * Cenario 3) Há um tratamento de exceções genérico para todos os verticles
 * Cenario 4) O bloqueio do loop de eventos apenas é logado, ele não é pego como exceção
 */
fun main(args : Array<String>) = runBlocking {
    val vertx = Vertx.vertx()

    println("Deploy Verticle sem tratamento de exceções")
    awaitResult<String>{vertx.deployVerticle(SemTratamentoExcecoes(), it)}

    delay(4000)

    println("Deploy Verticle com erro no Deploy como tratar:")
    try {
        val deploymentID = awaitResult<String> { vertx.deployVerticle(ErroNoDeploy(), it) }
    } catch (e : Exception) {
        println("Erro no deploy plotado com sucesso, faça o tratamento aqui: ${e.message}")
        println("Repare no código que o awaitResult permite pegar o erro com try/catch")
    }

    delay(4000)

    println("\nDeploy de um Verticle com tratamento de erros genérico no context")

    awaitResult<String>{vertx.deployVerticle(VerticleComTratamentoDeErrosDefault(), it)}

    delay(5000)

    println("""
        Vamos tentar executar o código que não tratou as exceções mas dessa vez
         vamos registrar um handler genérico para toda aplicação Vert.x
        """.trimIndent().replace("\n", ""))

    vertx.exceptionHandler {
        println("Aqui vão cair todos os erros não tratados")
        println("Vamos decidir encerrar a aplicação Vert.x a partir desse momento...")

        vertx.close()
    }

    delay(2000)

    val deploymentID = awaitResult<String>{vertx.deployVerticle(SemTratamentoExcecoes(), it)}
}

class ErroNoDeploy : AbstractVerticle() {
    override fun start() {
        throw Exception("Problema no deploy do Verticle")
    }
}

class SemTratamentoExcecoes : CoroutineVerticle() {
    override suspend fun start() {

        vertx.setTimer(2000, {
            throw Exception("Deu ruim e não tem tratamento de erros...")
        })
    }
}

class VerticleComTratamentoDeErrosDefault : AbstractVerticle() {
    override fun start() {
        //Registre o exception handler aqui
        context.exceptionHandler {error->
            println("Erro do Verticle pego no contexto, faça o tratamento aqui")
            println("Mensagem: ${error.message}")
        }

        vertx.setTimer(2000, {
            throw Exception("Aconteceu um problema, mas tá tranquilo")
        })
    }
}