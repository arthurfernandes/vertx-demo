/*
 * Hello World com Vert.x e Javascript
 *
 * Cria um servidor http na porta 8090
*/

vertx.createHttpServer().requestHandler(function (req) {
    req.response().putHeader("content-type", "text/html").end("<html><body><h1>Hello from vert.x Javascript!</h1></body></html>");
}).listen(8090);