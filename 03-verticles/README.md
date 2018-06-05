# Exemplo de implantação de Verticles

Nos exemplos apresentados é possível observar:

* Algumas formas de executar o *deploy*/*undeploy* de *Verticles*
* *Deploy* de múltiplos *Verticles*
    * O que acontece quando *Verticles* fazem implantação de outros *Verticles*?
* Executando código bloqueante com *executeBlocking* e criando *WorkerVerticles*
* Quebrando a [Regra de Ouro do Vert.x](https://vertx.io/docs/vertx-core/kotlin/#golden_rule)
* Utilizando as Corotinas do Kotlin - *CoroutineVerticle*
    * A mesma funcionalidade utilizando/não utilizando corotinas
    * O essencial de corotinas e executar o seu código no contexto do Vert.x
* Código asíncrono e exceções não tratadas, o que fazer e onde pegar as exceções?


Cada exemplo está em um pacote separado e existe uma função ```main``` comentada que explica o que irá ocorrer.

