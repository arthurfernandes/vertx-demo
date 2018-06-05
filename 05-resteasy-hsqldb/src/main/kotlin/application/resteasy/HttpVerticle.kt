package application.resteasy

import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.awaitResult
import org.jboss.resteasy.plugins.server.vertx.VertxRequestHandler
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment

private const val HTTP_PORT = 8080

class HttpVerticle : CoroutineVerticle() {
    override suspend fun start(){
        val httpServer = vertx.createHttpServer()
        val mainRouter = Router.router(vertx)

        val restEasyDeployment = VertxResteasyDeployment()
        restEasyDeployment.start()
        restEasyDeployment.registry.addPerInstanceResource(MovieRESTAPIController::class.java)

        val resteasyRequestHandler = VertxRequestHandler(vertx, restEasyDeployment)

        val restEasyRouter = Router.router(vertx)
        restEasyRouter.route().handler {
            resteasyRequestHandler.handle(it.request())
        }

        mainRouter.mountSubRouter("/api",restEasyRouter)

        httpServer.requestHandler(mainRouter::accept)

        awaitResult<HttpServer> { httpServer.listen(HTTP_PORT, it) }
    }
}