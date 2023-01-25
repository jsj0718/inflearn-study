package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core",
//        basePackageClasses = AutoAppConfig.class, (클래스로 지정하면 해당 클래스의 패키지부터 탐색 시작)
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    //2. @Autowired를 사용해도 되는 경우 (설정을 목적으로 하는 @Configuration 같은 곳에서는 Spring Container에서만 실행되므로 사용 가능)

//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    DiscountPolicy discountPolicy;
//
//    @Bean
//    OrderService orderService() {
//        return new OrderServiceImpl(memberRepository, discountPolicy);
//    }

//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}