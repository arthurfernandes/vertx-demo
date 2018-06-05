package application.database.hsql


import application.database.DatabaseService
import application.models.Movie
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.core.Future
import io.vertx.ext.sql.ResultSet
import io.vertx.kotlin.core.json.JsonArray
import io.vertx.kotlin.coroutines.awaitResult
import kotlinx.coroutines.experimental.launch

class HsqlDatabaseServiceImpl(
    private val dbClient: JDBCClient,
    private val readyHandler: Handler<AsyncResult<DatabaseService>>
) : DatabaseService {

    private enum class SqlQuery {
        CREATE_TABLE_MOVIES,
        GET_ALL_MOVIES,
        GET_MOVIE_BY_ID,
        CREATE_MOVIE
    }

    private val SQL_CREATE_TABLE_MOVIES =
        """CREATE TABLE IF NOT EXISTS "Movies" ("id" INTEGER IDENTITY, "name" VARCHAR(255));"""
    private val SQL_GET_ALL_MOVIES =
        """SELECT "id", "name" FROM "Movies";"""
    private val SQL_GET_MOVIE_BY_ID =
        """SELECT "id", "name" FROM "Movies" WHERE "id" = ?;"""
    private val SQL_INSERT_MOVIE =
        """INSERT INTO "Movies" ("name") VALUES (?)"""

    private val sqlQueries = hashMapOf(
        SqlQuery.CREATE_TABLE_MOVIES to SQL_CREATE_TABLE_MOVIES,
        SqlQuery.GET_ALL_MOVIES to SQL_GET_ALL_MOVIES,
        SqlQuery.GET_MOVIE_BY_ID to SQL_GET_MOVIE_BY_ID,
        SqlQuery.CREATE_MOVIE to SQL_INSERT_MOVIE
    )

    init {
        dbClient.getConnection {
            if (it.failed()) {
                readyHandler.handle(Future.failedFuture(it.cause()))
            } else {
                val connection = it.result()
                val query = sqlQueries.get(SqlQuery.CREATE_TABLE_MOVIES)
                connection.execute(query, {
                    connection.close()
                    if (it.failed()) {
                        readyHandler.handle(Future.failedFuture(it.cause()))
                    } else {
                        readyHandler.handle(Future.succeededFuture(this))
                    }
                })
            }
        }
    }

    override fun getAllMovies(resultHandler: Handler<AsyncResult<List<Movie>>>): DatabaseService {
        val query = sqlQueries.get(SqlQuery.GET_ALL_MOVIES)
        launch {
            val queryResult = awaitResult<ResultSet> { dbClient.query(query, it) }
            resultHandler.handle(Future.succeededFuture(queryResult.rows.map { Movie(it) }))
        }
        return this
    }

    override fun getMovieById(id : Int,resultHandler: Handler<AsyncResult<Movie?>>): DatabaseService {
        val query = sqlQueries.get(SqlQuery.GET_MOVIE_BY_ID)
        val params = JsonArray(id)
        dbClient.queryWithParams(query,params) { res ->
            if (res.succeeded()) {
                val movie = res.result().rows.map { Movie(it) }.firstOrNull()
                resultHandler.handle(Future.succeededFuture(movie))
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()))
            }
        }
        return this
    }

    override fun createMovie(movie: Movie, resultHandler: Handler<AsyncResult<Void>>): DatabaseService {
        val query = sqlQueries.get(SqlQuery.CREATE_MOVIE)
        val params = JsonArray(movie.name)
        dbClient.updateWithParams(query, params, { res ->
            if (res.succeeded()) {
                resultHandler.handle(Future.succeededFuture())
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()))
            }
        })
        return this
    }
}