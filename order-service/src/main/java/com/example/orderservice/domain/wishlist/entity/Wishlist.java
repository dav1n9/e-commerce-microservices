package com.example.orderservice.domain.wishlist.entity;


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
@Table(name = "wishlists")
public class Wishlist extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    private String name;

    private Long userId;

    @OneToMany(mappedBy = "wishlist")
    private List<WishlistItem> wishlistItems = new ArrayList<>();

    @Builder
    public Wishlist(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }
}
