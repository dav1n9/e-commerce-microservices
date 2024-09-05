package com.example.orderservice.domain.order.repository;

import com.example.orderservice.domain.order.entity.Order;
import com.example.orderservice.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatusAndCreatedAtBetween(OrderStatus status, LocalDateTime start, LocalDateTime end);
    List<Order> findByStatusAndUpdatedAtBetween(OrderStatus status, LocalDateTime start, LocalDateTime end);
}
