package org.example;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.core.json.JsonObject;

public class MainApp {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        var server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        // Enable body handling (for POST, PUT, etc.)
        router.route().handler(BodyHandler.create());

        // GET endpoint
        router.get("/hello").handler(ctx -> {
            ctx.response().end("Hello from Vert.x!");
        });

        // POST endpoint
        router.post("/employee").handler(ctx -> {
            JsonObject data = ctx.body().asJsonObject();  // ✅ Correct method
            String name = data.getString("name");
            String role = data.getString("role");

            JsonObject response = new JsonObject()
                    .put("message", "Employee " + name + " with role " + role + " added");

            ctx.response()
                    .putHeader("content-type", "application/json")
                    .end(response.encode());
        });

        server.requestHandler(router).listen(8888);
        System.out.println("Server running on http://localhost:8888");
    }
}

