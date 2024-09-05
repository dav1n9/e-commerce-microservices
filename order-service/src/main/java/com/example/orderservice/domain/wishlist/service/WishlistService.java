package com.example.orderservice.domain.wishlist.service;

import com.example.orderservice.domain.wishlist.dto.AddWishlistItemRequestDto;
import com.example.orderservice.domain.wishlist.dto.UpdateWishlistItemRequestDto;
import com.example.orderservice.domain.wishlist.dto.WishlistItemResponseDto;
import com.example.orderservice.domain.wishlist.dto.WishlistResponseDto;
import com.example.orderservice.domain.wishlist.entity.Wishlist;
import com.example.orderservice.domain.wishlist.entity.WishlistItem;
import com.example.orderservice.domain.wishlist.repository.WishlistItemRepository;
import com.example.orderservice.domain.wishlist.repository.WishlistRepository;
import com.example.orderservice.global.client.ItemResponseDto;
import com.example.orderservice.global.client.ItemServiceClient;
import com.example.orderservice.global.client.UserResponseDto;
import com.example.orderservice.global.client.UserServiceClient;
import com.example.orderservice.global.exception.BusinessException;
import com.example.orderservice.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ItemServiceClient itemServiceClient;
    private final UserServiceClient userServiceClient;

    public WishlistItemResponseDto addWishlistItem(Long wishlistId, AddWishlistItemRequestDto request) {
        Wishlist wishlist = findByWishlist(wishlistId);
        ItemResponseDto itemDto = findItemById(request.getItemId());
        WishlistItem wishlistItem = wishlistItemRepository.save(
                WishlistItem.builder()
                        .wishlist(wishlist)
                        .itemId(itemDto.getId())
                        .itemName(itemDto.getName())
                        .itemPrice(itemDto.getPrice())
                        .count(request.getCount())
                        .build());
        return new WishlistItemResponseDto(wishlistItem);
    }

    public WishlistResponseDto createWishlist(Long userId, String name) {
        UserResponseDto userDto = findUserById(userId);
        Wishlist wishlist = wishlistRepository.save(
                Wishlist.builder()
                        .userId(userDto.getUserId())
                        .name(name)
                        .build());
        return new WishlistResponseDto(wishlist);
    }

    public WishlistResponseDto createDefaultWishlist(Long userId) {
        Wishlist wishlist = wishlistRepository.save(
                Wishlist.builder()
                        .userId(userId)
                        .name("기본 위시리스트")
                        .build());
        return new WishlistResponseDto(wishlist);
    }

    public WishlistResponseDto findWishlist(Long wishlistId) {
        Wishlist wishlist = findByWishlist(wishlistId);
        return new WishlistResponseDto(wishlist);
    }

    public List<WishlistResponseDto> findAllWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId).stream().map(WishlistResponseDto::new).toList();
    }

    @Transactional
    public WishlistItemResponseDto updateItemCount(Long wishlistItemId, UpdateWishlistItemRequestDto request) {
        WishlistItem wishlistItem = findByWishlistItem(wishlistItemId);
        wishlistItem.setCount(request.getCount());
        return new WishlistItemResponseDto(wishlistItem);
    }

    public void deleteItem(Long wishlistItemId) {
        WishlistItem wishlistItem = findByWishlistItem(wishlistItemId);
        wishlistItemRepository.delete(wishlistItem);
    }

    private Wishlist findByWishlist(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WISHLIST_NOT_FOUND));
    }

    private ItemResponseDto findItemById(Long itemId) {
        return itemServiceClient.findItemById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
    }

    private UserResponseDto findUserById(Long userId) {
        return userServiceClient.findUserById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private WishlistItem findByWishlistItem(Long wishlistItemId) {
        return wishlistItemRepository.findById(wishlistItemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WISHLIST_ITEM_NOT_FOUND));
    }
}
