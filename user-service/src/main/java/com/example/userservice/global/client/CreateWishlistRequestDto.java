package com.example.userservice.global.client;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateWishlistRequestDto {
    private String name;

    public CreateWishlistRequestDto(String name) {
        this.name = name;
    }
}
