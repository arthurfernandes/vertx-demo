package vertxcoroutines.demo

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

class VerticleA : AbstractVerticle() {
    override fun start(startFuture: Future<Void>) {
        Thread.sleep(1000)
        println ("Deploy de A com sucesso")
        startFuture.complete()
    }
}

class VerticleB : AbstractVerticle() {
    override fun start(startFuture: Future<Void>) {
        println ("Deploy de B com sucesso!")
        startFuture.complete()
    }
}