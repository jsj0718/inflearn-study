package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = applicationContext.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = applicationContext.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = applicationContext.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean {

        // 싱글톤 내부에서 실행할 때마다 프로토타입을 새로 생성하는 방법1. 스프링 컨테이너를 싱글톤 내부에서 주입받아 호출될 때마다 프로토타입을 생성한다. -> 굉장히 비효율적
//        private final PrototypeBean prototypeBean;
//        @Autowired
//        ApplicationContext applicationContext;

        // 싱글톤 내부에서 실행할 때마다 프로토타입을 새로 생성하는 방법2. Spring에서 제공하는 ObjectProvider, ObjectFactory를 사용한다.
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        // 싱글톤 내부에서 실행할 때마다 프로토타입을 새로 생성하는 방법3. Java에서 제공하는 Provider를 사용한다. (외부 라이브러리 추가 필요)
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
//            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class); // 방법1
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject(); // 방법2
            PrototypeBean prototypeBean = prototypeBeanProvider.get(); // 방법3
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void close() {
            System.out.println("PrototypeBean.close" + this);
        }
    }
}
