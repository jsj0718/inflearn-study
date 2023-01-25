package jpabook.jpashop.service.query;

import jpabook.jpashop.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemDtoV2 {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDtoV2(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}
