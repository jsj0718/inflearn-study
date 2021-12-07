package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    //스프링에서 자동으로 AnnotationAwareAspectJAutoProxyCreator (자동 프록시 생성기) 빈 후처리기가 등록된다.
    //이 빈 후처리기는 모든 Advisor를 탐색하고, pointcut을 통해 해당 빈 클래스의 모든 메소드 중 하나라도 적용 대상이라면 proxy를 생성한다.
//    @Bean
    public Advisor advisor1(LogTrace trace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(trace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor2(LogTrace trace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..))"); //패키지 위치 지정

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(trace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace trace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))"); //패키지 위치 지정

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(trace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
