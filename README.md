# vertx-demo

Um conjunto de aplicações para demonstrar algumas funcionalidades do toolkit  [**Vert.x**](https://vertx.io/).
Foi escolhido *Kotlin* como linguagem para os exemplos e é demonstrado a utilização de corotinas em conjunto com o Vert.x.

O projeto é dividido em um conjunto de 5 partes, sendo que as 3 primeiras utilizam apenas funcionalidades do [Vert.x Core](https://vertx.io/docs/vertx-core/kotlin) e buscam mostrar em detalhes como o Vert.x funciona por baixo dos panos.

1. Hello World em Kotlin e Javascript (Vert.x é um *toolkit* poliglota)
1. Utilização do *event-bus* do Vert.x:
    * Publish-Subscribe
    * Request-Response
    * Point-to-Point
    * Utilizando JSON como linguagem padrão para comunicação no barramento de eventos
1. Exemplos *Verticles*
    * Algumas formas de executar o *deploy*/*undeploy* de *Verticles*
    * *Deploy* de múltiplos *Verticles*
    * Executando código bloqueante com *executeBlocking* e criando *WorkerVerticles*
    * Quebrando a [Regra de Ouro do Vert.x](https://vertx.io/docs/vertx-core/kotlin/#golden_rule)
    * Utilizando as Corotinas do Kotlin - *CoroutineVerticle*
    * Tratando Exceções na sua aplicação
1. Utilizando o [Vert.x Web](https://vertx.io/docs/vertx-web/kotlin/)
    * Servindo arquivos estáticos
    * Criando uma API REST simples
    * Utilizando Sock.js para se comunicar com o *front end*
    * Estendendo o barramento de eventos do vert.x para o frontend
1. Exemplo de uma aplicação com API REST e banco de dados embarcado:
    * Criando uma API REST com o RESTEasy
    * Utilizando o [Vert.x Service Proxy](https://vertx.io/docs/vertx-service-proxy/java/) para expor um serviço no barramento de eventos
    * Utilizando o HSQLDB como banco de dados embarcado da aplicação

Cada parte possui um projeto gradle específico que pode ser executado e importado separadamente, ou então se preferir é possível importar o projeto raiz.

Nos exemplos é utilizado o [plugin do gradle para o vert.x](https://github.com/jponge/vertx-gradle-plugin), mas na maioria dos exemplos você pode executar a função *main* dos arquivos que apresentam um determinado exemplo.

