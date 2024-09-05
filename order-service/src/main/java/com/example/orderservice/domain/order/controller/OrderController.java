package com.example.orderservice.domain.order.controller;

import com.example.orderservice.domain.messagequeue.KafkaProducer;
import com.example.orderservice.domain.order.dto.CreateOrderRequestDto;
import com.example.orderservice.domain.order.dto.OrderResponseDto;
import com.example.orderservice.domain.order.service.OrderService;
import com.example.orderservice.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/orders")
public class OrderController {

    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(HttpServletRequest request,
                                                        @RequestBody CreateOrderRequestDto orderRequest) throws Exception {
        OrderResponseDto orderDto = orderService.save(jwtUtil.getUserIdFromToken(request), orderRequest);
        kafkaProducer.send("remove-item-stock", orderDto);
        return ResponseEntity.ok().body(orderDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAllOrder(HttpServletRequest request) {
        return ResponseEntity.ok().body(orderService.findAll(jwtUtil.getUserIdFromToken(request)));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(HttpServletRequest request,
                                                        @PathVariable Long orderId) {
        OrderResponseDto orderDto = orderService.cancelOrder(jwtUtil.getUserIdFromToken(request), orderId);
        kafkaProducer.send("add-item-stock", orderDto);
        return ResponseEntity.ok().body(orderDto);
    }

    @PatchMapping("/{orderId}/return")
    public ResponseEntity<OrderResponseDto> returnOrder(HttpServletRequest request,
                                                        @PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.returnOrder(jwtUtil.getUserIdFromToken(request), orderId));
    }
}
