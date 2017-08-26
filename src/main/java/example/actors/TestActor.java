package example.actors;

import akka.actor.*;
import akka.event.*;
import example.services.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

@Component
@Scope("prototype")
public class TestActor extends UntypedActor {
  private final LoggingAdapter log = Logging.getLogger(context().system(), this);

  private final TestService testService;

  public TestActor(TestService testService) {
    this.testService = testService;
  }

  @Override
  public void onReceive(Object message) throws Throwable {
    log.info("Service method result: {}", testService.something(22));
    log.info("Message received: {}", message.toString());
  }
}
