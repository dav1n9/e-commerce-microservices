package com.example.orderservice.domain.order.entity;

import com.example.orderservice.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(String address, OrderStatus status, Long userId, List<OrderItem> orderItems) {
        this.address = address;
        this.status = status;
        this.userId = userId;
        this.orderItems = orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getTotalPrice() {
        Integer totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
