package com.example.orderservice.domain.order.service;

import com.example.orderservice.domain.order.dto.CreateOrderItemDto;
import com.example.orderservice.domain.order.dto.CreateOrderRequestDto;
import com.example.orderservice.domain.order.repository.OrderRepository;
import com.example.orderservice.global.client.ItemResponseDto;
import com.example.orderservice.global.client.ItemServiceClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemServiceClient itemServiceClient;


    @BeforeEach
    public void insertQuantity() {
        ItemResponseDto itemResponseDto = itemServiceClient.updateQuantity(1L, 100).orElseThrow();
        System.out.println(itemResponseDto.getStockQuantity());
    }

    @AfterEach
    public void delete() {
        orderRepository.deleteAll();
    }

    @Test
    public void 주문() throws InterruptedException {
        orderService.save(1L, new CreateOrderRequestDto(List.of(new CreateOrderItemDto(1L, 1))));

        Thread.sleep(1000);

        ItemResponseDto itemById = itemServiceClient.findItemById(1L).orElseThrow();
        Assertions.assertEquals(99, itemById.getStockQuantity());
    }

    @Test
    public void 동시에_100명이_주문() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            Long id = (long) i;
            executorService.submit(() -> {
                try {
                    orderService.save(id, new CreateOrderRequestDto(List.of(new CreateOrderItemDto(1L, 1))));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Thread.sleep(1000);

        ItemResponseDto itemById = itemServiceClient.findItemById(1L).orElseThrow();
        Assertions.assertEquals(0, itemById.getStockQuantity());
    }
}