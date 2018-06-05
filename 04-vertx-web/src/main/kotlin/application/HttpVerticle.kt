package application

import application.api.RESTAPIRouter
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.sockjs.SockJSHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.awaitResult
import io.vertx.kotlin.ext.web.handler.sockjs.BridgeOptions
import io.vertx.kotlin.ext.web.handler.sockjs.PermittedOptions
import io.vertx.kotlin.ext.web.handler.sockjs.SockJSHandlerOptions

const val WEBROOT_DIR = "client"
const val PERMITTED_ADDRESS = POSITIONS_ADDRESS
/**
 * Esse Verticle tem as seguintes responsabilidades:
 * - Servir arquivos estáticos da pasta client
 * - Estender o barramento de eventos para o FrontEnd
 * - Mostrar a criação de uma simples API REST
 *
 * A configuração de porta pode ser obtida durante o deploy
 *
 */
class HttpVerticle : CoroutineVerticle() {
    override suspend fun start() {
        val port = context.config().getInteger("port")?:8080

        val router = Router.router(vertx)

        router.route("/event-bus/*").handler(getSockJSHandler())

        router.mountSubRouter("/api", RESTAPIRouter.create(vertx))

        router.route().handler(StaticHandler.create().setWebRoot(WEBROOT_DIR))

        val server = awaitResult<HttpServer> {
            vertx.createHttpServer()
                    .requestHandler(router::accept)
                    .listen(port, it)}

        println("Listening on ${server.actualPort()}")
    }

    private fun getSockJSHandler() : SockJSHandler {
        val sockJSHandler = SockJSHandler.create(vertx, SockJSHandlerOptions(
            heartbeatInterval = 2000
        ))

        val permittedOptions = PermittedOptions(
            address = PERMITTED_ADDRESS
        )

        sockJSHandler.bridge(BridgeOptions(
            //O que os clientes podem adicionar ao barramento de eventos
            inboundPermitted = listOf(permittedOptions),
            //O que os clientes podem ouvir do barramento de eventos
            outboundPermitted = listOf(permittedOptions)
        ))

        return sockJSHandler
    }
}
