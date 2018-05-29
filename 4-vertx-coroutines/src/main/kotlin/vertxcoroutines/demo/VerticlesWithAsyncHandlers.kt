package vertxcoroutines.demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx

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
    vertx.deployVerticle(WithAsyncHandlers())
}

class WithAsyncHandlers : AbstractVerticle() {
    override fun start () {
        vertx.fileSystem().readFile("4-vertx-coroutines/ArquivoConfiguracaoMaroto") {
            if (it.succeeded()) {
                println (it.result())
                vertx.deployVerticle(VerticleA()) {
                    if (it.succeeded())
                        vertx.deployVerticle(VerticleB()) {
                            if (it.succeeded())
                                println("Aplicação deployada com sucesso!")
                            else
                                println("Verticle B não foi deployado")
                        }
                    else
                        println ("Verticle A não foi deployado")
                }            }
            else
                println ("Erro ao ler arquivo de configuração!")
        }
    }
}

