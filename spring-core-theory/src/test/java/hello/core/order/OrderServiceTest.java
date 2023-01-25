package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        // given
        Long memberId = 1L;
        Member member = new Member(memberId, "정대만", Grade.VIP);
        memberService.join(member);

        // when
        Order order = orderService.createOrder(memberId, "토비의 스프링 3.1", 30000);

        // then
        org.assertj.core.api.Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

//    @Test
//    @DisplayName("필드 주입은 순수한 자바코드로 테스트를 할 때 할 수 있는 방법이 없다.")
//    void findInjectionTest() {
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        Assertions.assertThrows(NullPointerException.class,
//                () -> orderService.createOrder(1L, "슬램덩크", 10000));
//    }
}