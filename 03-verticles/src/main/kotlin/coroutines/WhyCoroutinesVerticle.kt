package coroutines

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.DeploymentOptions
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.awaitResult

/**
 * Esse exemplo demonstra a utilização de um Verticle com Corotinas do Kotlin
 *
 * Compare o WhyCoroutinesVerticle com o NoCoroutinesVerticle
 *
 * O que ambos fazem é executar na ordem sequencial:
 * => Ler um arquivo
 * => Caso tenha lido com sucesso, leia os conteúdos como JSON
 * => Imprima a mensagem na tela
 * => Faça implantação do Verticle A
 * => Faça a implantação do Verticle B (2 instancias)
 * => Crie um servidor na porta especificada no arquivo, (ou 8080 - 8090 por padrão)
 *
 * Obs: o arquivo lido se localiza na pasta resources (configuration.json)
 *
 */
fun main(args : Array<String>) {
    val vertx = Vertx.vertx()

    vertx.deployVerticle(NoCoroutinesVerticle()) {
        if (it.succeeded()) {
            println("You finally deployed the Hadouken Verticle, time to relax")
        } else {
            println("Deu ruim na implantacao do seu Verticle...")
            println(it.cause())
        }
    }

    //Bloquear a Thread main por 5 segundos, o vert.x continua rodando normalmente
    //Ele nao depende da Thread main
    Thread.sleep(5000)
    println("\n\n##############\n\n")

    //Fazendo a implantacao do Verticle baseado em Corotinas
    vertx.deployVerticle(WhyCoroutinesVerticle(), {
        if (it.succeeded()) {
            println("Funcionou legal o CoroutineVerticle")
        } else {
            println("Deu ruim na implantacao do seu Verticle...")
            println(it.cause())
        }
    })
}

class WhyCoroutinesVerticle : CoroutineVerticle() {
    /**
     * Quando declarar parâmetros como lateinit tenha certeza que você
     * os inicializa antes de usar. Isso pode causar NullPointerExceptions
     * **/
    lateinit var deploymentIDOfA : String
    lateinit var deploymentIDOfB : String

    /*
     * Basta colocar o marcador suspend na nossa função de start.
     * Assim que retornarmos dessa função o Verticle estará implantado =)
     * Se algum erro ocorrer no meio do caminho ele não será implantado =)
     */
    override suspend fun start() {
        /** Não esqueca de passar o it que é o responsável por retornar o seu valor
         *  Do contrario ele vai congelar nesse ponto
         *  Mas sem travar o barramento de eventos, pelo menos
         *  **/
        val fileContent = awaitResult<Buffer> {
            vertx.fileSystem().readFile("configuration.json", it) }

        val fileContentAsJSON = JsonObject(fileContent)
        val message = fileContentAsJSON.getString("message")

        println("You got the message ${message}")

        deploymentIDOfA = awaitResult<String> {
            vertx.deployVerticle(AVerticle(), it)
        }

        deploymentIDOfB = awaitResult {
            vertx.deployVerticle(BVerticle::class.java, DeploymentOptions(instances = 2), it)
        }

        val port = fileContentAsJSON.getInteger("coroutine-port")?:8090

        val server = awaitResult<HttpServer>{handler->
            vertx.createHttpServer().requestHandler({
            it.response().end("Coroutine Verticle")
            }).listen(port, handler)
        }

        println("Listening on port: ${server.actualPort()}")

        //Se você chegou até esse ponto o seu verticle vai ter sido implantado com sucesso
    }

    /*
     * Da mesma forma para o stop
     */
    override suspend fun stop() {

    }
}

/**
 * Hadouken oriented programming
 */
class NoCoroutinesVerticle : AbstractVerticle() {
    lateinit var deploymentIDofA : String
    lateinit var deploymentIDofB : String

    override fun start(startFuture : Future<Void>) {
        vertx.fileSystem().readFile("configuration.json") {
            if (it.succeeded()) {
                val fileContent = it.result()
                val fileContentAsJSON = JsonObject(fileContent)
                val message = fileContentAsJSON.getString("message")

                println("You got the message: ${message}")

                vertx.deployVerticle(AVerticle()) {

                    if (it.succeeded()) {
                        deploymentIDofA = it.result()

                        vertx.deployVerticle(BVerticle::class.java, DeploymentOptions(
                            instances = 2
                        )) {
                            //Quantas chaves mais vamos abrir e fechar?
                            if (it.succeeded()){

                                deploymentIDofB = it.result()

                                val port = fileContentAsJSON.getInteger("hadouken-port")?:8080

                                vertx.createHttpServer()
                                    .requestHandler({
                                        it.response().end("Deployed Hadouken Verticle")
                                    }).listen(port) {
                                        if (it.succeeded()) {
                                            //Time to relax:
                                            println("Listening on port: ${it.result().actualPort()}")
                                            startFuture.complete()
                                        } else {
                                            startFuture.fail(it.cause())
                                        }
                                    }
                            } else {
                                startFuture.fail(it.cause())
                            }
                        }
                    } else {
                        startFuture.fail(it.cause())
                    }
                }

            } else {
                startFuture.fail(it.cause())
            }
        }
    }
}

class AVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploy of Verticle A")
        //Descomente a linha abaixo para simular um erro durante a implantação
        //throw Exception("Se f.... deu mal")
    }
}

class BVerticle : AbstractVerticle() {
    override fun start() {
        println("Deploy of Verticle B")
    }
}