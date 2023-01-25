package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    @Order(2)
    @Aspect
    public static class LogAspect {
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @Order(1)
    @Aspect
    public static class TxAspect {
        @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
            try {
                log.info("[Transaction 시작] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[Transaction 커밋] {}", joinPoint.getSignature());
                return result;
            } catch (Exception e) {
                log.info("[Transaction 롤백] {}", joinPoint.getSignature());
                throw e;
            } finally {
                log.info("[Resource Release] {}", joinPoint.getSignature());
            }
        }
    }
}
