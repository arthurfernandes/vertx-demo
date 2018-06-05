package simple.publish.subscribe

import io.vertx.core.AbstractVerticle
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import java.util.*

internal val MESSAGE_ADDRESS = "feed.messages"

/**
 * Publica uma mensagem aleatória no endereço feed.messages a cada 1 segundo
 */
class PublishVerticle : AbstractVerticle() {
    private val random = Random()

    private val messageDictionary = listOf(
        "banana",
        "to",
        "fly",
        "over",
        "air",
        "ball",
        "check",
        "brazilian",
        "routine",
        "the",
        "some",
        "ok",
        "pretty"
    )

    override fun start() {
        println("Deploy publish verticle in Thread: ${Thread.currentThread().name}")

        vertx.setPeriodic(1000, {
            val randomMessage = generateRandomMessage()

            vertx.eventBus().publish(MESSAGE_ADDRESS, randomMessage)
        })
    }

    override fun stop() {
        println("Undeploy publish verticle")
    }

    fun generateRandomMessage() : String {
        val messageLength = random.nextInt(5) + 3

        val builder = StringBuilder()

        for (i in 0..messageLength) {
            val randomIndex = random.nextInt(messageDictionary.size)
            val randomWord = messageDictionary.get(randomIndex)
            builder.append("$randomWord ")
        }

        return builder.toString()
    }

}