package com.example.orderservice.domain.wishlist.dto;

import com.example.orderservice.domain.wishlist.entity.Wishlist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class WishlistResponseDto {
    private Long wishlistId;
    private String name;
    private List<WishlistItemResponseDto> wishlistItems = new ArrayList<>();

    public WishlistResponseDto(Wishlist wishlist) {
        this.wishlistId = wishlist.getId();
        this.name = wishlist.getName();
        this.wishlistItems = wishlist.getWishlistItems().stream().map(WishlistItemResponseDto::new).toList();
    }
}
