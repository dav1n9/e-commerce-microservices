package com.example.userservice.domain.user.dto;

import com.example.userservice.global.common.EncryptDecryptConverter;
import jakarta.persistence.Convert;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @Convert(converter = EncryptDecryptConverter.class)
    private String email;

    private String password;
}