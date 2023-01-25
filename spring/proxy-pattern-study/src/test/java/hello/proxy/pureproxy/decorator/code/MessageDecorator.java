package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private Component target;

    public MessageDecorator(Component target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");
        String result = target.operation();
        String decoratorResult = "*****" + result + "*****";
        log.info("Decorator 전={}, 후={}", result, decoratorResult);
        return decoratorResult;
    }
}
