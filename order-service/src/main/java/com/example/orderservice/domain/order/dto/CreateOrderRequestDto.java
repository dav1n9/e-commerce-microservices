package com.example.orderservice.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateOrderRequestDto {
    private List<CreateOrderItemDto> orderItems;
}
