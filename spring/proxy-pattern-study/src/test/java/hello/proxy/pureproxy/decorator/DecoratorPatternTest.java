package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        DecoratorPatternClient client = new DecoratorPatternClient(new RealComponent());
        client.execute();
    }

    @Test
    void decorator1() {
        DecoratorPatternClient client = new DecoratorPatternClient(new MessageDecorator(new RealComponent()));
        client.execute();
    }

    @Test
    void decorator2() {
        DecoratorPatternClient client = new DecoratorPatternClient(new TimeDecorator(new MessageDecorator(new RealComponent())));
        client.execute();
    }
}
