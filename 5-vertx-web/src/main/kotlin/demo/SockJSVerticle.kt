package demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.http.HttpServerOptions
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

fun main(args : Array<String>) {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(
            SockJSVerticle::class.java,
            DeploymentOptions()
    )
}

class SockJSVerticle : AbstractVerticle() {
    override fun start() {
        val server = vertx.createHttpServer(HttpServerOptions(
                port = 8080
        ))

        val router = Router.router(vertx)

        router.get("/").handler({
            it.response().end("Você veio para a raiz do projeto")
        })

        val jsonObj = json {
            obj (
                "abcdef" to array("1", "2")
            )
        }

        println(jsonObj)

        router.get("/islands").handler({
            it.response()
                .putHeader("Content-Type", "application/json")
                .end(jsonObj.toString())
        })

        server.requestHandler({router.accept(it)})

        server.listen()
    }
}
