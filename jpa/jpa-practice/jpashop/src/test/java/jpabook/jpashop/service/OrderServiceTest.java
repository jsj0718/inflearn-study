package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void 상품주문() {
        //given
        Member member = createMember("member1", "도시", "거리", "11111");

        Book book = createBook("JPA", 10000, 10);

        int orderCount = 5;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(orderId);

        assertThat(OrderStatus.ORDER).isEqualTo(findOrder.getStatus()); //상품 주문 시 상태는 ORDER
        assertThat(1).isEqualTo(findOrder.getOrderItems().size()); //주문한 상품 종류 수가 정확해야 한다.
        assertThat(10000 * 5).isEqualTo(findOrder.getTotalPrice()); //주문 가격은 가격 * 수량이다.
        assertThat(5).isEqualTo(book.getStockQuantity()); //주문 수량만큼 재고가 줄어야 한다.


    }

    @Test
    void 주문취소() {
        //given
        Member member = createMember("member1", "도시", "거리", "11111");
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 5;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        assertThat(5).isEqualTo(book.getStockQuantity());

        //when
        orderService.cancelOrder(orderId);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertThat(OrderStatus.CANCEL).isEqualTo(findOrder.getStatus()); //주문 취소 시 상태는 CANCEL 이다.
        assertThat(10).isEqualTo(book.getStockQuantity()); //주문 취소 시 재고가 원복되어야 한다.
    }

    @Test
    void 상품주문_재고수량초과() {
        //given
        Member member = createMember("member1", "도시", "거리", "11111");
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 11;

        //when, then
        Assertions.assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), book.getId(), orderCount),
                "상품 재고 초과 시 예외가 발생해야 한다."
        );
    }

    private Member createMember(String name, String city, String street, String zipcode) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address(city, street, zipcode));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}