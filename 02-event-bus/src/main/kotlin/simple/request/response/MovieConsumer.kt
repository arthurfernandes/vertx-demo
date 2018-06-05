package simple.request.response

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.kotlin.core.eventbus.DeliveryOptions
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

/**
 * O MovieConsumer encapsula o formato das requisições ao MovieService
 * dado uma instância do vertx criada
 *
 * Repare que em todas as requisições send é passado um cabeçalho action
 */
class MovieConsumer(val vertx : Vertx){

    fun addMovie(movie : Movie) {

        println("Consumer Executing in thread: ${Thread.currentThread().name}")

        vertx.eventBus().send<String>(
                MOVIE_SERVICE_ADDRESS,
                json { obj(
                    "name" to movie.name,
                    "year" to movie.year
                )}.toString(),
                DeliveryOptions(headers = mapOf("action" to MovieActions.ADD.action)),
                {
                    if (it.succeeded()) {
                        println(it.result().body())
                    } else {
                        println(it.cause())
                    }
                }
        )
    }

    fun getAllMovies() {
        vertx.eventBus().send<String>(
            MOVIE_SERVICE_ADDRESS,
            "",
            DeliveryOptions(headers = mapOf("action" to MovieActions.ALL.action)),
            {
                if (it.succeeded()) {
                    println(it.result().body())
                } else {
                    println(it.cause())
                }
            }
        )
    }

    fun getAfterYearMovies(year : Int) {
        vertx.eventBus().send<String>(
            MOVIE_SERVICE_ADDRESS,
            year.toString(),
            DeliveryOptions(headers = mapOf("action" to MovieActions.AFTER_YEAR.action)),
            {
                if (it.succeeded()) {
                    println(it.result().body())
                } else {
                    println(it.cause())
                }
            }
        )
    }
}