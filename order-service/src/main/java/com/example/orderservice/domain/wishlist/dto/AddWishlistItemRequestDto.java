package com.example.orderservice.domain.wishlist.dto;

import lombok.Getter;

@Getter
public class AddWishlistItemRequestDto {
    private Long itemId;
    private Integer count;
}
