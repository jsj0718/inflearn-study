package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    //Retry를 파라미터에 적으면 @annotation에 해당 타입 정보가 들어가게 되므로 경로를 적지 않아도 된다.
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {}, retry={}", joinPoint.getSignature(), retry);

        int maxValue = retry.value();
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= maxValue; retryCount++) {
            try {
                log.info("[retry] try count = {}/{}", retryCount, maxValue);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }
}
