package application.resteasy

import application.database.DB_SERVICE_ADDRESS
import application.database.DatabaseService
import application.models.Movie
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.awaitResult
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.experimental.launch
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/api/movie")
class MovieRESTAPIController(@Context private val vertx: Vertx)  {

    private val dbServiceAddress = DB_SERVICE_ADDRESS
    private val dbService = DatabaseService.createProxy(vertx, dbServiceAddress)

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllMovies(@Suspended asyncResponse: AsyncResponse) {
        launch(vertx.dispatcher()) {
            val listMovie = awaitResult<List<Movie>> { dbService.getAllMovies(it) }
            asyncResponse.resume(listMovie)
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMovieById(@Suspended asyncResponse: AsyncResponse, @PathParam("id") id: Int) {
        launch(vertx.dispatcher()) {
            val movie = awaitResult<Movie?> { dbService.getMovieById(id, it) }
            movie?.let {
                asyncResponse.resume(movie)
            } ?: asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build())
        }
    }


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    fun createMovie(@Suspended asyncResponse: AsyncResponse, @Valid movie : Movie) {
        launch(vertx.dispatcher()) {
            awaitResult<Void> { dbService.createMovie(movie, it) }
            asyncResponse.resume(Response.status(Response.Status.CREATED).build())
        }
    }
}