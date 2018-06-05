# Utilizando Vert.x Web

Esse exemplo demonstra a utilização do módulo Web do Vert.x, com as seguintes funcionalidades:

* Servindo arquivos estáticos
* Crianco uma simples API REST (No exemplo 5 vamos usar o RESTEasy para essa tarefa)
* Estendendo o barramento de eventos com sock.js e o vertx-eventbus.js
    * Como adicionar permissões para o Front End adicionar / coletar informações do barramento de eventos
* Também é demonstrado como processar a sua parte de arquivos do FrontEnd para uma localização específica no *fatjar*


Para conseguir executar o exemplo:

````bash

./gradlew vertxRun

```

Você também pode executar o *fatjar* para verificar que o código da sua pasta cliente foi empacotado:

````bash

./gradlew runShadow

```
