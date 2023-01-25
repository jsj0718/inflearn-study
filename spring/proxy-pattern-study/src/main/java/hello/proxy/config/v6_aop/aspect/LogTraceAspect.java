package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace trace;

    public LogTraceAspect(LogTrace trace) {
        this.trace = trace;
    }

    //advisor
    @Around("execution(* hello.proxy.app..*(..))") //pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { //advice
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = trace.begin(message);

            Object result = joinPoint.proceed();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
