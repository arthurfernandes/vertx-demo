package application.api

import application.services.MovieService
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import models.Movie

class RESTAPIRouter {
    companion object {
        fun create (vertx : Vertx) : Router {
            val router = Router.router(vertx)

            //Necessário para ler o corpo da requisicao
            router.route("/movies")
                    .method(HttpMethod.POST).handler(BodyHandler.create())

            router.get("/movies").handler(this::handleGetAllMovies)

            router.post("/movies").handler(this::handleAddMovie)

            return router
        }

        fun handleGetAllMovies(rc : RoutingContext){
            val movies = MovieService.getAllMovies()

            val jsonString = json{ obj(
                "movies" to movies
            )}.toString()

            rc.response()
                .putHeader("Content-Type", "application/json")
                .end(jsonString)
        }

        fun handleAddMovie(rc : RoutingContext) {
            val jsonBody = rc.bodyAsJson
            val movieName = jsonBody.getString("name")
            val movieYear = jsonBody.getInteger("year")

            if (movieName != null && movieYear != null) {
                val movie = Movie(movieName, movieYear)

                MovieService.addMovie(movie)

                rc.response()
                    .setStatusCode(HttpResponseStatus.CREATED.code())
                    .putHeader("Content-Type","application/json")
                    .end(JsonObject.mapFrom(movie).toString())
            } else {
                rc.response()
                    .setStatusCode(400)
                    .end(json {obj (
                        "error" to "Wrong parameters specified"
                    )}.toString())
            }
        }
    }
}