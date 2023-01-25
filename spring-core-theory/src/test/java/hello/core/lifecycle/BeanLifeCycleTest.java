package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient networkClient = applicationContext.getBean(NetworkClient.class);
        applicationContext.close(); // Spring Container 종료, ConfigurableApplicationContext가 필요

    }

    @Configuration
    static class LifeCycleConfig {
        // 초기화 소멸 메서드 지정 방법 2. 빈에 등록하는 방법
//        @Bean (initMethod = "init")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
