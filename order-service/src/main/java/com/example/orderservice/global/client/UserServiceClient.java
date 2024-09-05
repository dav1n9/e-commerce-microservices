package com.example.orderservice.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v2/users/{userId}")
    Optional<UserResponseDto> findUserById(@PathVariable Long userId);

}