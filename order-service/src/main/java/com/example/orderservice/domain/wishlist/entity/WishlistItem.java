package com.example.orderservice.domain.wishlist.entity;

import com.example.orderservice.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wishlist_items")
public class WishlistItem extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    private Long itemId;

    private String itemName;

    private Integer itemPrice;

    private Integer count;

    @Builder
    public WishlistItem(Wishlist wishlist, Long itemId, String itemName, Integer itemPrice, Integer count) {
        this.wishlist = wishlist;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
