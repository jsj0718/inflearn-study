package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * ObjectProvider(Provider), ApplicationContext를 사용한 지연(Lazy) 조회
 * 객체 조회 시점을 스프링 빈 생성 시점이 아니라 실제 객체 사용 시점으로 지연할 수 있다.
 */
@Slf4j
@Component
public class CallServiceV2 {

//    private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    //지연 조회 방법
    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
//        this.applicationContext = applicationContext;
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); //외부 메서드 조회
    }

    public void internal() {
        log.info("call internal");
    }
}
