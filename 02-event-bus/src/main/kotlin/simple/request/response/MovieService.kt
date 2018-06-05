package simple.request.response

import io.vertx.core.AbstractVerticle
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

internal val MOVIE_SERVICE_ADDRESS = "movie.service"

enum class MovieActions(val action : String) {
    ADD("add"),
    ALL("all"),
    AFTER_YEAR("after-year"),
}

/**
 * Representa um serviço escutando no endereço movie.service
 * Toda requisição deve especificar um cabeçalho action:
 *      all => get all movies
 *      add => in the body specify a movie as json
 *      after-year => list all movies that have a release date after some year
 *
 *
 * Nesse exemplo é possível observar como é serializado/deserializado o JSON de forma simples
 * O exemplo utiliza a biblioteca do vertx + kotlin para json.
 * Um exemplo mais detalhado de utilização de JSON está no exemplo com RESTEasy (projeto 05)
 */
class MovieService : AbstractVerticle() {

    private val movies = mutableListOf(
        Movie("Avengers Infinity War", 2018),
        Movie("Inception", 2010),
        Movie("Titanic", 1998)
    )

    override fun start() {
        //O tipo de objeto que você especifica no template (nesse caso <String>)
        //É o tipo esperado para o objeto no corpo da requisição
        vertx.eventBus().consumer<String>(MOVIE_SERVICE_ADDRESS, {

            println("Handling Service request in: ${Thread.currentThread().name}")

            val action  = it.headers().get("action")

            when (action) {
                MovieActions.ADD.action -> addMovieHandler(it)
                MovieActions.ALL.action -> allMoviesHandler(it)
                MovieActions.AFTER_YEAR.action -> afterYearHandler(it)
                else -> {
                    it.fail(1, "Non existent action string in header action")
                }
            }
        })
    }

    private fun addMovieHandler(message: Message<String>) {
        val movieJSON = JsonObject(message.body())

        try {
            val name = movieJSON.getString("name")
            val year = movieJSON.getInteger("year")

            val movie = Movie(name, year)

            movies.add(movie)

            message.reply("Success in adding $name")
        } catch(e : Exception) {
            message.fail(1, e.message)
        }
    }

    private fun allMoviesHandler(message: Message<String>) {
        message.reply(json { obj(
            "movies" to array( movies.map {
                obj(
                        "name" to it.name,
                        "year" to it.year
                )
            })
        )}.toString())
    }

    private fun afterYearHandler(message : Message<String>) {
        try {
            val year = message.body().toInt()

            message.reply(json {obj (
                    "movies" to array( movies
                            .filter { it.year > year}
                            .map {obj(
                                "name" to it.name,
                                "year" to it.year
                            )}
                    ))}.toString())

        } catch (e : NumberFormatException) {
            message.fail(1, "Year format error")
        }
    }
}