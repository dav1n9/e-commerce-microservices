package com.example.userservice.domain.user.dto;

import com.example.userservice.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String username;
    private String address;
    private String phoneNumber;
    private String email;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }
}
