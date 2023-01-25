package jpabook.jpashop.service.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class OrderDtoV2 {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    //        private List<OrderItem> orderItems; //엔티티가 있으므로 안된다. (완전히 엔티티와의 의존을 끊어야 함)
    private List<OrderItemDtoV2> orderItemDtos;

    public OrderDtoV2(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
//            order.getOrderItems().stream().forEach(o -> o.getItem().getName());
//            orderItems = order.getOrderItems();
        orderItemDtos = order.getOrderItems().stream()
                .map(OrderItemDtoV2::new)
                .collect(toList());
    }
}
