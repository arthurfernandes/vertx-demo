package application.database
import application.database.hsql.HsqlDatabaseServiceImpl
import application.models.Movie
import io.vertx.codegen.annotations.Fluent
import io.vertx.codegen.annotations.GenIgnore
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.ext.jdbc.JDBCClient

@ProxyGen
@VertxGen
interface DatabaseService {

    @GenIgnore
    companion object {
        // Para usar um DB diferente, basta alterar somente o método create
        fun create(
            dbClient: JDBCClient,
            readyHandler: Handler<AsyncResult<DatabaseService>>
        ) = HsqlDatabaseServiceImpl(dbClient, readyHandler)

        fun createProxy(vertx: Vertx, address: String): DatabaseService {
            return DatabaseServiceVertxEBProxy(vertx, address)
        }
    }

    @Fluent
    fun getAllMovies(resultHandler: Handler<AsyncResult<List<Movie>>>): DatabaseService

    @Fluent
    fun getMovieById(id : Int,resultHandler: Handler<AsyncResult<Movie?>>): DatabaseService

    @Fluent
    fun createMovie(movie: Movie, resultHandler: Handler<AsyncResult<Void>>): DatabaseService
}


