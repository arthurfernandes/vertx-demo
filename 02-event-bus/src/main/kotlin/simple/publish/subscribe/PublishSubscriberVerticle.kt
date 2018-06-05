package simple.publish.subscribe

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.core.DeploymentOptions

fun main(args : Array<String>){
    val vertx = Vertx.vertx()

    vertx.deployVerticle(PublishSubscriberVerticle())
}

/**
 * Um simples exemplo de um Publish-Subscriber
 *
 * Nesse exemplo é feito a implantação de um Verticle Publisher que publica mensagens aleatórias
 * a cada 1 segudno e duas intâncias de um Verticle Subscriber que receberão a mesma mensagem.
 */
class PublishSubscriberVerticle : AbstractVerticle() {
    override fun start() {

        vertx.deployVerticle(PublishVerticle())

        vertx.deployVerticle(SubscriberVerticle::class.java, DeploymentOptions(
            instances = 2
        ))
    }
}