package application

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.experimental.delay
import models.Position
import java.util.*

const val POSITIONS_ADDRESS = "feed.localizacoes"

/**
 * Publica uma Localizacao Aleatória a cada segundo
 */
class LocationVerticle : CoroutineVerticle() {
    val random = Random()

    suspend override fun start() {
        while(true) {

            val position = generateRandomPosition()

            vertx.eventBus().publish(POSITIONS_ADDRESS, JsonObject.mapFrom(position).toString())

            delay(1000)
        }
    }

    fun generateRandomPosition() : Position {
        val id = UUID.randomUUID()
        val lat = random.nextDouble() * 90 - 90.0
        val lng = random.nextDouble() * 180 - 180.0
        val alt = random.nextDouble() * 1000 - 200

        val position = Position(id.toString(), lat, lng, alt)

        return position
    }
}