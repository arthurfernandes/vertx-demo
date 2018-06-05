package simple.request.response

import io.vertx.core.Vertx
import io.vertx.kotlin.core.DeploymentOptions

/**
 * Faz a implantação de 4 instâncias do Verticle MovieService
 *
 * Nesse exemplo você terá uma ferramenta de linha de comando
 * para executar ações no MovieService. As ações são:
 *   - add: adicionar um filme
 *   - all : retorna uma lista com todos os filmes
 *   - after-year: retorna uma lista com os filmes lançados após um determinado ano
 *
 * Cada ação envia uma mensagem e espera por uma resposta de forma asíncrona do MovieService
 * Para que as respostas possam aparecer no console é criado
 * um delay de 1 segundo para observar a resposta do MovieService
 *
 * Repare que cada vez que você executa uma ação no MovieService ele executa o seu código
 * em uma instância diferente
 * (o Vert.x faz o balanceamento nas diversas instâncias que estão escutando em um endereço - no exemplo são 4)
 */
fun main(args : Array<String>) {
    val vertx = Vertx.vertx()

    vertx.deployVerticle(MovieService::class.java, DeploymentOptions(
        instances = 4
    ))

    val movieConsumer = MovieConsumer(vertx)

    while(true) {
        println("Digite uma ação: add, all, after-year")

        try {

            val action = readLine()!!.trim().toLowerCase()
            when (action) {
                MovieActions.ADD.action -> {
                    println("Digite o nome do filme. Ex: Avengers")
                    val name = readLine()!!.toString()
                    println("Digite o ano de lançamento. Ex: 2008")
                    val year = readLine()!!.toInt()

                    val movie = Movie(name, year)

                    movieConsumer.addMovie(movie)
                    Thread.sleep(1000)
                }
                MovieActions.ALL.action -> {
                    movieConsumer.getAllMovies()
                    Thread.sleep(1000)
                }
                MovieActions.AFTER_YEAR.action -> {
                    println("Digite um ano. Ex: 1995")

                    val year = readLine()!!.toInt()

                    movieConsumer.getAfterYearMovies(year)
                    Thread.sleep(1000)
                }
                else -> {
                    println("Ação especificada não existe. As opções são: all, add, after-year\n")
                }
            }
        } catch(e : Exception) {
            println(e.cause)
        }
    }
}