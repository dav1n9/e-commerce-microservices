package com.example.orderservice.domain.order.dto;

import com.example.orderservice.domain.order.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long itemId;
    private String itemName;
    private Integer orderPrice;
    private Integer count;
    private Integer totalPrice;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.itemId = orderItem.getItemId();
        this.itemName = orderItem.getItemName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        this.totalPrice = orderItem.getTotalPrice();
    }
}
