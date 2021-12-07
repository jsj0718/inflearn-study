package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace trace) {
        OrderControllerV2 OrderControllerV2 = new OrderControllerV2(orderServiceV2(trace));

        ProxyFactory proxyFactory = new ProxyFactory(OrderControllerV2);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderControllerV2 proxy = (OrderControllerV2) proxyFactory.getProxy();

        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), OrderControllerV2.getClass());

        return proxy;
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace trace) {
        OrderServiceV2 OrderServiceV2 = new OrderServiceV2(orderRepositoryV2(trace));

        ProxyFactory proxyFactory = new ProxyFactory(OrderServiceV2);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderServiceV2 proxy = (OrderServiceV2) proxyFactory.getProxy();

        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), OrderServiceV2.getClass());

        return proxy;
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace trace) {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();

        ProxyFactory proxyFactory = new ProxyFactory(orderRepositoryV2);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderRepositoryV2 proxy = (OrderRepositoryV2) proxyFactory.getProxy();

        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderRepositoryV2.getClass());

        return proxy;
    }

    private Advisor getAdvisor(LogTrace trace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(trace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
