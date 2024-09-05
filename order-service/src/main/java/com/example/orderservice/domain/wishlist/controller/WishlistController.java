package com.example.orderservice.domain.wishlist.controller;

import com.example.orderservice.domain.wishlist.dto.*;
import com.example.orderservice.domain.wishlist.service.WishlistService;
import com.example.orderservice.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{wishlistId}")
    public ResponseEntity<WishlistItemResponseDto> addWishlistItem(@PathVariable Long wishlistId,
                                                @RequestBody AddWishlistItemRequestDto wishlistItemDto) {
        return ResponseEntity.ok().body(wishlistService.addWishlistItem(wishlistId, wishlistItemDto));
    }

    @PostMapping()
    public ResponseEntity<WishlistResponseDto> createWishlist(HttpServletRequest request,
                                                              @RequestBody CreateWishlistRequestDto wishlistDto) {
        return ResponseEntity.ok().body(wishlistService.createWishlist(jwtUtil.getUserIdFromToken(request), wishlistDto.getName()));
    }

    @PostMapping("/default")
    public ResponseEntity<WishlistResponseDto> createDefaultWishlist(@RequestParam("userId") Long userId) {
        return ResponseEntity.ok().body(wishlistService.createDefaultWishlist(userId));
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<WishlistResponseDto> findWishlist(@PathVariable Long wishlistId) {
        return ResponseEntity.ok().body(wishlistService.findWishlist(wishlistId));
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> findAllWishlist(HttpServletRequest request) {
        return ResponseEntity.ok().body(wishlistService.findAllWishlist(jwtUtil.getUserIdFromToken(request)));
    }

    @PatchMapping("/items/{wishlistItemId}")
    public ResponseEntity<WishlistItemResponseDto> updateItemCount (@PathVariable Long wishlistItemId,
                                                @RequestBody UpdateWishlistItemRequestDto wishlistItemDto) {
        return ResponseEntity.ok().body(wishlistService.updateItemCount(wishlistItemId, wishlistItemDto));
    }

    @DeleteMapping("/items/{wishlistItemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long wishlistItemId) {
        wishlistService.deleteItem(wishlistItemId);
        return ResponseEntity.ok().build();
    }
}
