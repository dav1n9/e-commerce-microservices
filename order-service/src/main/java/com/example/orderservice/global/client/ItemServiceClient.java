package com.example.orderservice.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "item-service")
public interface ItemServiceClient {

    @GetMapping("/api/v2/items/{itemId}")
    Optional<ItemResponseDto> findItemById(@PathVariable Long itemId);

    @PostMapping("/api/v2/items/{itemId}")
    Optional<ItemResponseDto> updateQuantity(@PathVariable Long itemId, @RequestBody Integer quantity);

    @GetMapping("/errorful/case1")
    String case1();

    @GetMapping("/errorful/case2")
    String case2();

    @GetMapping("/errorful/case3")
    String case3();

}
