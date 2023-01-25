package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import jpabook.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * XToOne 관계에서 즉시 로딩 발생
 * Order
 * Order -> Member (다대일)
 * Order -> Delivery (일대일)
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * 양방향 문제 발생 (무한 반복)
     * 해결 방법
     * 1. 한 쪽에 @JsonIgnore를 붙인다. -> 지연 로딩이므로 프록시 객체이기 때문에 리플렉션 할 수 없어서 오류 발생함.
     * 2. Hibernate5 Module을 사용하여 해결 가능 (라이브러리 등록 필요)
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기화
        }
        return orders;
    }

    /**
     * DTO 활용
     * 해결된 점
     * 1. 엔티티를 직접 반환 X (API Spec 유지 가능)
     * 2. V1에서 한 작업들을 모두 생략 가능
     * 
     * 문제
     * 1. N + 1 문제가 발생한다. (N + 1 -> 1 + 회원 N + 배송 N) -> EAGER로도 해결 X (예상치 못한 쿼리 발생, 성능 문제)
     * 2. DTO 또한 List가 아닌 다른 객체로 감싸서 반환해야 한다.
     */
    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        return orderService.findOrders(new OrderSearch()).stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
    }

    /**
     * fetch join 활용
     * 해결된 점
     * 1. V2에서의 N+1 문제 해결
     *
     * 문제
     * 1. 모든 엔티티 정보를 조인하여 가져옴
     */
    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        return orderRepository.findAllWithMemberDelivery().stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
    }

    /**
     * JPA에서 DTO를 바로 조회
     * 해결된 점
     * 1. V3에서 모든 데이터를 조회한 것을 필요한 것만 조회하도록 변경
     *
     * V3와 V4의 트레이드 오프
     * 1. V3의 경우 외부 변경없이 성능 튜닝 가능 (유연하게 사용 가능)
     * 2. V4의 경우 화면에 최적화되어 있지만, 재사용성이 낮기 때문에 특수한 경우에만 사용 가능 (변경에 취약함)
     * 3. 또한 API Spec이 repository에 있기 때문에 취지에 맞지 않음 (repository는 본래 엔티티 객체 그래프 조회 용도로 사용되어야 함)
     * 4. 조건절에서 인덱싱이 성능을 크게 좌우할 뿐 필드 수는 크게 영향을 주지 않는다. -> 단 트래픽이 많고, 필드 수가 굉장히 많은 경우에는 고려해야 함
     */
    @GetMapping("api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }
    
    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        //DTO에서 엔티티를 매개변수로 받아도 괜찮다.
        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //LAZY 초기화
        }
    }

}
