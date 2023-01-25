package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.service.query.OrderDtoV2;
import jpabook.jpashop.service.query.OrderQueryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderQueryService orderQueryService;

    /**
     * 엔티티 반환 (API Spec 불안정)
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        orders.forEach(order -> {
            order.getMember().getName();
            order.getDelivery().getAddress();
            order.getOrderItems().stream().forEach(orderItem -> orderItem.getItem().getName());
        });

        return orders;
    }

    /**
     * DTO 반환 (N + 1 문제 발생)
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        return orderRepository.findAllByString(new OrderSearch()).stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    /**
     * fetch join 사용
     * 문제점
     * 1. 컬렉션의 경우 데이터 뻥튀기 현상 발생 (일대다, 다대다의 경우 다에 맞춰서 데이터 수가 형성됨)
     * <p>
     * 해결 방법
     * 1. distinct 사용 (DB에서 distinct는 완전히 같은 데이터여야 중복 제거 되지만, JPA에서는 같은 식별자, 객체이면 중복 제거)
     * -> fetch join 하는 순간 페이징이 불가능해진다.
     * -> SQL로 번역되는 것이 아니라 메모리로 모든 DB 데이터를 가져온 후 메모리에서 페이징을 한다. (매우 위험)
     */
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        return orderRepository.findAllWithItem().stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return orderRepository.findAllWithMemberDelivery(offset, limit).stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

    @GetMapping("/api/osiv/orders")
    public List<OrderDtoV2> ordersOsiv() {
        return orderQueryService.osivOff();
    }

    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        //        private List<OrderItem> orderItems; //엔티티가 있으므로 안된다. (완전히 엔티티와의 의존을 끊어야 함)
        private List<OrderItemDto> orderItemDtos;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
//            order.getOrderItems().stream().forEach(o -> o.getItem().getName());
//            orderItems = order.getOrderItems();
            orderItemDtos = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
        }
    }

    @Data
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }

}
