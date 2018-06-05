package demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx

/**
 * Esse exemplo mostra como fazer o deploy de Verticles em múltiplas linguagens de programação
 *
 * No exemplo é feito o deploy de um Verticle em Javascript e de um Verticle feito em Kotlin
 *
 * O Verticle em Kotlin escuta na porta 8080 e o em Javascript na porta 8090
 *
 */
fun main(args : Array<String>) {
    val vertx = Vertx.vertx()

    //Ao escrever js no prefixo estamos indicando ao Vert.x qual fábrica utilizar para criar o Verticle
    vertx.deployVerticle("js:HelloWorldVerticle.js")

    //Aqui estamos passando uma instância criada de um verticle para o Vert.x
    vertx.deployVerticle(MainVerticle())
}

/*
 * Cria um servidor http na porta 8080
 */
class MainVerticle() : AbstractVerticle(){
    override fun start(){
        vertx.createHttpServer()
            .requestHandler({
                it.response()
                    .putHeader("Content-Type", "text/html")
                    .end("Hello World from Kotlin =)")
            }).listen(8080)
    }
}
