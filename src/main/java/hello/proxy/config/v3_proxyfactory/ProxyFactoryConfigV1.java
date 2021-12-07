package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.*;
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
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace trace) {
        OrderControllerV1 OrderControllerV1 = new OrderControllerV1Impl(orderServiceV1(trace));

        ProxyFactory proxyFactory = new ProxyFactory(OrderControllerV1);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderControllerV1 proxy = (OrderControllerV1) proxyFactory.getProxy();

        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), OrderControllerV1.getClass());

        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace trace) {
        OrderServiceV1 OrderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(trace));

        ProxyFactory proxyFactory = new ProxyFactory(OrderServiceV1);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderServiceV1 proxy = (OrderServiceV1) proxyFactory.getProxy();

        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), OrderServiceV1.getClass());

        return proxy;
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace trace) {
        OrderRepositoryV1Impl orderRepositoryV1 = new OrderRepositoryV1Impl();

        ProxyFactory proxyFactory = new ProxyFactory(orderRepositoryV1);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderRepositoryV1 proxy = (OrderRepositoryV1) proxyFactory.getProxy();

        log.info("ProxyFactory proxy={}, target={}", proxy.getClass(), orderRepositoryV1.getClass());

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
