package hello.core.order;

import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderSerivceImplTest {

    @Test
    void createOrder() {
        MemberRepository memberRepository = new MemoryMemberRepository();
        Member member = new Member(1L, "정대만", Grade.VIP);
        memberRepository.save(member);

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new RateDiscountPolicy());
        Order order = orderService.createOrder(1L, "포카칩", 1000);

        assertThat(order.getDiscountPrice()).isEqualTo(100);
    }
}
