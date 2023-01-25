package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = applicationContext.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = applicationContext.getBean("statefulService", StatefulService.class);
        
        // ThreadA : userA가 1000원 주문
        int userAPrice = statefulService1.order("userA", 1000);
        // ThreadB : userB가 5000원 주문
        int userBPrice = statefulService2.order("userB", 5000);

        // ThreadA : userA가 조회 (1000원이 나와야하지만 5000원이 나옴) -> 상태를 유지하면 안된다.
//        System.out.println("itemA = " + statefulService1.getPrice());
        // ThreadB : userB가 조회
//        System.out.println("itemB = " + statefulService2.getPrice());

        assertThat(userBPrice).isEqualTo(5000);
    }

    @Configuration
    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}