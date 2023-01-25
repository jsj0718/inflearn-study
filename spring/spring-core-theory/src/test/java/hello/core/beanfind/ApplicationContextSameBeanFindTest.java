package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> applicationContext.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
    void findBeanByName() {
        DiscountPolicy discountPolicy1 = applicationContext.getBean("discountPolicy1", DiscountPolicy.class);
        assertThat(discountPolicy1).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 타입 모두 조회하기")
    void findAllBeanByType() {
        Map<String, DiscountPolicy> beansOfType = applicationContext.getBeansOfType(DiscountPolicy.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + ", value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    // static class는 scope을 사용한 클래스에서만 쓰겠다는 의미
    @Configuration
    static class SameBeanConfig {
        @Bean
        public DiscountPolicy discountPolicy1() {
            return new FixDiscountPolicy();
        }

        @Bean
        public DiscountPolicy discountPolicy2() {
            return new RateDiscountPolicy();
        }
    }
}
