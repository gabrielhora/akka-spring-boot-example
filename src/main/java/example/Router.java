package example;

import akka.actor.*;
import akka.http.javadsl.server.*;
import org.springframework.stereotype.*;

import static akka.actor.ActorRef.*;

@Component
public class Router extends AllDirectives {

  private final ActorSystem system;
  private final ActorRef testActor;

  public Router(ActorSystem actorSystem, SpringExtension springExtension) {
    this.system = actorSystem;
    testActor = system.actorOf(springExtension.props("testActor"));
  }

  Route createRoute() {
    return route(
      path("hello", () ->
        get(() -> {
          testActor.tell("Hello World", noSender());
          return complete("<h1>Hello World</h1>");
        })));
  }
}
