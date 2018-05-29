package vertxcoroutines.demo

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.awaitResult

/*
 * O que queremos fazer?
 * 1) Ler o arquivo de configuração.
 * 2) Deploy do Verticle A
 * 3) Deploy do Verticle B
 * OBS: De forma sequencial!
 *
 */

fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(WithCoroutines())
}


class WithCoroutines : CoroutineVerticle() {
    override suspend fun start(){
        val arquivo = awaitResult<Buffer> { vertx.fileSystem().readFile("4-vertx-coroutines/ArquivoConfiguracaoMaroto",it) }
        println (arquivo)
        awaitResult<String> { vertx.deployVerticle(VerticleA(),it) }
        awaitResult<String> { vertx.deployVerticle(VerticleB(),it) }
    }
}