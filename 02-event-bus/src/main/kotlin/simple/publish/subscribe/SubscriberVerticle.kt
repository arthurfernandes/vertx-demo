package simple.publish.subscribe

import io.vertx.core.AbstractVerticle

/**
 * Escuta mensagens publicadas no endereço feed.messages
 *
 * Quando recebe a mensagem imprime no console em qual thread está executando e a mensagem recebida
 */
class SubscriberVerticle : AbstractVerticle() {
    override fun start() {
        vertx.eventBus().consumer<String>(MESSAGE_ADDRESS, {
            println("""
                Consuming in Thread: ${Thread.currentThread().name}
                Message: ${it.body()}
            """.trimIndent())
        })
    }
}