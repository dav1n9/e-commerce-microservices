package com.example.orderservice.global.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "item-service")
public interface ItemServiceClient {

    @GetMapping("/api/v2/items/{itemId}")
    Optional<ItemResponseDto> findItemById(@PathVariable Long itemId);

    @GetMapping("/errorful/case1")
    String case1();

    @GetMapping("/errorful/case2")
    String case2();

    @GetMapping("/errorful/case3")
    String case3();

}
