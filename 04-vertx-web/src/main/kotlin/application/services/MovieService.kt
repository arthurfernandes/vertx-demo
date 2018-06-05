package application.services

import models.Movie

//Essa estrutura não é Thread Safe caso múltiplos HttpVerticles acessem
object MovieService {
    private val movies = mutableListOf(
            Movie("Avengers Infinity War", 2018),
            Movie("Inception", 2010),
            Movie("Titanic", 1998)
        )

    fun getAllMovies() : List<Movie>{
        return movies
    }

    fun addMovie(movie : Movie) {
        movies.add(movie)
    }
}