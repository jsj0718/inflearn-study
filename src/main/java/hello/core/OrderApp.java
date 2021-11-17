package hello.core;

import hello.core.member.*;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {


    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();
//        OrderService orderService = new OrderServiceImpl();

        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member member = new Member(memberId, "정대만", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "토비의 스프링 3.1", 20000);

        System.out.println(order);
        System.out.println("최종 금액 : " + order.calculatePrice());
    }
}
