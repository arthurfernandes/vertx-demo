package coroutines

import io.vertx.core.AbstractVerticle
import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import io.vertx.kotlin.coroutines.awaitResult
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.TimeUnit

/**
 * Os Futures do Vert.x agora possuem o método await para suspender a execução até que sejam resolvidos
 */
fun main(args: Array<String>) = runBlocking {
    val vertx = Vertx.vertx()

    val deploymentID = awaitResult<String>{vertx.deployVerticle(CoroutineFutures(), it)}

    println("Undeploy agendado para daqui 10 segundos")

    delay(10, TimeUnit.SECONDS)

    //Undeploy depois de 10 segundos
    awaitResult<Void> {vertx.undeploy(deploymentID, it)}

    println("Undeploy realizado com sucesso")

    delay(5, TimeUnit.SECONDS)

    println("Parando o vertx...")
    //Encerrar o Vertx
    vertx.close()
}

class CoroutineFutures : CoroutineVerticle() {
    override suspend fun start() {

        println("Deploy de forma asincrona de 2 verticles")

        val verticleA1Future = Future.future<String>()
        val verticleA2Future = Future.future<String>()

        vertx.deployVerticle(object : AbstractVerticle(){}, verticleA1Future)
        vertx.deployVerticle(object : CoroutineVerticle(){}, verticleA2Future)

        //Futures possuem o método await para suspender a execução nesse ponto
        //O CompositeFuture nos permite compor Futures das mais diversas maneiras
        val result = CompositeFuture.all(verticleA1Future, verticleA2Future).await()

        if (result.succeeded()) {
            //Podemos chamar o result tranquilamente nesse ponto
            println("ID Verticle 1: ${verticleA1Future.result()}")
            println("ID Verticle 2: ${verticleA2Future.result()}")
        } else {
            //Não vamos deixar o verticle ser implantado
            throw result.cause()
        }
    }
}