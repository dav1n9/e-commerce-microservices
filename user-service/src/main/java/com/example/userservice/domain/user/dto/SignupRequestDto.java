package com.example.userservice.domain.user.dto;

import com.example.userservice.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;

    public User toEntity(String password) throws Exception {
        return User.builder()
                .username(username)
                .address(address)
                .phoneNumber(phoneNumber)
                .email(email)
                .password(password)
                .build();
    }
}