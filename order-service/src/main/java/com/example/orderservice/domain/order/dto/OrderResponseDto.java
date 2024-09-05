package com.example.orderservice.domain.order.dto;

import com.example.orderservice.domain.order.entity.Order;
import com.example.orderservice.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private String address;
    private OrderStatus status;
    private Integer orderTotalPrice;
    private List<OrderItemResponseDto> orderItems = new ArrayList<>();

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.address = order.getAddress();
        this.status = order.getStatus();
        orderTotalPrice = order.getTotalPrice();
        this.orderItems = order.getOrderItems().stream().map(OrderItemResponseDto::new).toList();
    }
}
