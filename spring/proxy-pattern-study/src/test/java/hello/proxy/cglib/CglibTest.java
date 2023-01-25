package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer(); //CGLIB를 만드는 구현체
        enhancer.setSuperclass(ConcreteService.class); //프록시의 상속받을 구체 클래스를 지정
        enhancer.setCallback(new TimeMethodInterceptor(target)); //실행할 로직 지정
        ConcreteService proxy = (ConcreteService) enhancer.create(); //proxy 생성

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();
    }
}
