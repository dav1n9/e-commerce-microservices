package com.example.orderservice.domain.order.entity;

import com.example.orderservice.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private Long itemId;

    private Integer orderPrice;

    private String itemName;

    private Integer count;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderItem(Long itemId, Integer orderPrice, String itemName, Integer count, Order order) {
        this.itemId = itemId;
        this.orderPrice = orderPrice;
        this.itemName = itemName;
        this.count = count;
        this.order = order;
    }

    public Integer getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
