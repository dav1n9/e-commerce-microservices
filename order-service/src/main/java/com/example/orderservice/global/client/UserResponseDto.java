package com.example.orderservice.global.client;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String username;
    private String address;
    private String phoneNumber;
    private String email;
}
