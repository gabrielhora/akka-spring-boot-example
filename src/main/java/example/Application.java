package example;

import akka.*;
import akka.actor.*;
import akka.http.javadsl.*;
import akka.http.javadsl.model.*;
import akka.stream.*;
import akka.stream.javadsl.*;
import org.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;

import java.io.*;
import java.util.concurrent.*;

@SpringBootApplication
public class Application {
  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws IOException {
    final ApplicationContext context = SpringApplication.run(Application.class, args);
    final ActorSystem system = context.getBean(ActorSystem.class);
    final Http http = Http.get(system);
    final ActorMaterializer materializer = ActorMaterializer.create(system);

    final Router router = context.getBean(Router.class);
    final Flow<HttpRequest, HttpResponse, NotUsed> flow = router.createRoute().flow(system, materializer);
    final CompletionStage<ServerBinding> binding = http
      .bindAndHandle(flow, ConnectHttp.toHost("0.0.0.0", 8080), materializer);

    log.info("Server online at http://localhost:8080/\nPress RETURN to stop...");
    System.in.read();

    binding
      .thenCompose(ServerBinding::unbind)
      .thenAccept(unbound -> system.terminate());
  }
}
