package com.example.orderservice.domain.wishlist.repository;

import com.example.orderservice.domain.wishlist.entity.Wishlist;
import com.example.orderservice.domain.wishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByWishlist(Wishlist wishlist);
}
