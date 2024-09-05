package com.example.userservice.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @PostMapping("/api/v2/wishlists/default")
    WishlistResponseDto createDefaultWishlist(@RequestParam("userId") Long userId);
}
