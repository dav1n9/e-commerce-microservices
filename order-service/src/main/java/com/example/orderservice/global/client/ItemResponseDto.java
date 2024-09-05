package com.example.orderservice.global.client;

import lombok.Getter;

@Getter
public class ItemResponseDto {

    private Long id;
    private String name;
    private Integer price;
    private Integer stockQuantity;

}