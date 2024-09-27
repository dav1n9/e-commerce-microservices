package com.example.orderservice.domain.order.dto;

import com.example.orderservice.domain.order.entity.Order;
import com.example.orderservice.domain.order.entity.OrderItem;
import com.example.orderservice.global.client.ItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderItemDto {
    private Long itemId;
    private Integer count;

    public OrderItem toEntity(Order order, ItemResponseDto itemDto) {
        return OrderItem.builder()
                .order(order)
                .itemId(itemDto.getId())
                .orderPrice(itemDto.getPrice())
                .itemName(itemDto.getName())
                .count(count)
                .build();
    }
}
