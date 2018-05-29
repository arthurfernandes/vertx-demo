package vertxcoroutines.demo

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.concurrent.thread

fun main(args: Array<String>) = runBlocking {
    //createOneMillionThreads()
    createOneMillionCoroutines()

}

fun createOneMillionThreads() {
    val oneMillionThreads = List(1_000_000) {
        thread {
            esperaXSegundoBloqueando(5)
        }
    }
    oneMillionThreads.forEach { it.join(0) }
}

suspend fun createOneMillionCoroutines() {
    val oneMillionCoroutines = launch {
        val listJob = List(1_000_000) {
            launch {
                esperaXSegundoSuspendendo(5)
            }
        }
        listJob.forEach { it.join() }
    }
    oneMillionCoroutines.join()
}


// Pontos de suspensão necessitam do modificador especial SUSPEND

fun esperaXSegundoBloqueando(x: Int) {
    Thread.sleep(x * 1000L)
}

suspend fun esperaXSegundoSuspendendo(x: Int) {
    delay(x * 1000L)
}