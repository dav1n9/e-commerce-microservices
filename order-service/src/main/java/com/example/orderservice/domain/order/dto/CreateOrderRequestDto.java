package com.example.orderservice.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderRequestDto {
    private List<CreateOrderItemDto> orderItems;
}
