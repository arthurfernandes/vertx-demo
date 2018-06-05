package simple.ping.pong

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx

internal val PING_PONG_ADDRESS = "ping-pong"

fun main(args : Array<String>) {
    val vertx = Vertx.vertx()

    vertx.deployVerticle(PingPongVerticle())
}

/**
 * Simula uma aperto de mão de três vias como no TCP, mas utilizando o barramento de eventos do Vert.x
 * A envia um PING
 * B responde com PONG
 * A responde com PING-PONG
 */
class PingPongVerticle : AbstractVerticle() {
    override fun start() {
        vertx.deployVerticle(PingVerticle())
        vertx.deployVerticle(PongVerticle())
    }
}

class PingVerticle : AbstractVerticle() {

    override fun start() {
        vertx.setPeriodic(3000, {
            startPingPongConversation()
        })
    }

    fun startPingPongConversation() {
        println("Kickstarting PingPong Conversation")
        vertx.eventBus().send<String>(PING_PONG_ADDRESS, "PING", {
            if (it.succeeded()) {
                //Print the replied message
                println("A received: ${it.result().body()}")

                it.result().reply("PING-PONG")

            } else {
                println(it.cause())
            }
        })
    }
}

class PongVerticle : AbstractVerticle() {
    override fun start() {

        //Listen for Ping Messages
        vertx.eventBus().consumer<String>(PING_PONG_ADDRESS, {
            println("B received ${it.body()}")

            it.reply<String>("PONG") {
                if (it.succeeded()) {
                    println("B received ${it.result().body()}")
                } else {
                    println(it.cause())
                }
            }
        })

    }
}
