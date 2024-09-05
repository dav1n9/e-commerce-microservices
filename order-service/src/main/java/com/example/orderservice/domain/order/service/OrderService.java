package com.example.orderservice.domain.order.service;

import com.example.orderservice.domain.order.dto.CreateOrderItemDto;
import com.example.orderservice.domain.order.dto.CreateOrderRequestDto;
import com.example.orderservice.domain.order.dto.OrderResponseDto;
import com.example.orderservice.domain.order.entity.Order;
import com.example.orderservice.domain.order.entity.OrderItem;
import com.example.orderservice.domain.order.entity.OrderStatus;
import com.example.orderservice.domain.order.repository.OrderItemRepository;
import com.example.orderservice.domain.order.repository.OrderRepository;
import com.example.orderservice.global.client.ItemResponseDto;
import com.example.orderservice.global.client.ItemServiceClient;
import com.example.orderservice.global.client.UserResponseDto;
import com.example.orderservice.global.client.UserServiceClient;
import com.example.orderservice.global.exception.BusinessException;
import com.example.orderservice.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemServiceClient itemServiceClient;
    private final UserServiceClient userServiceClient;

    @Transactional
    public OrderResponseDto save(Long userId, CreateOrderRequestDto request) {
        UserResponseDto userDto = findUserById(userId);

        Order order = orderRepository.save(Order.builder()
                .userId(userDto.getUserId())
                .address(userDto.getAddress())
                .status(OrderStatus.ORDER_COMPLETED)
                .build());

        // orderItem 생성 후 추가
        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderItemDto orderItem : request.getOrderItems()) {
            ItemResponseDto itemDto = findItemById(orderItem.getItemId());
            orderItems.add(orderItem.toEntity(order, itemDto));
        }
        orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);

        return new OrderResponseDto(order);
    }

    public List<OrderResponseDto> findAll(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(OrderResponseDto::new).toList();
    }

    @Transactional
    public OrderResponseDto cancelOrder(Long userId, Long orderId) {
        Order order = findOrderById(orderId);

        if (!Objects.equals(order.getUserId(), userId))
            throw new BusinessException(ErrorCode.NOT_USER_ORDER);

        if (order.getStatus() != OrderStatus.ORDER_COMPLETED && order.getStatus() != OrderStatus.SHIPPING) {
            throw new BusinessException(ErrorCode.ORDER_CANCELLATION_NOT_ALLOWED);
        }

        order.setStatus(OrderStatus.CANCELED);
        return new OrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto returnOrder(Long userId, Long orderId) {
        Order order = findOrderById(orderId);

        if (!Objects.equals(order.getUserId(), userId))
            throw new BusinessException(ErrorCode.NOT_USER_ORDER);

        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new BusinessException(ErrorCode.ORDER_NOT_DELIVERED);
        }

        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        if (order.getUpdatedAt().isBefore(oneDayAgo)) {
            throw new BusinessException(ErrorCode.RETURN_PERIOD_EXPIRED);
        }

        order.setStatus(OrderStatus.RETURN_REQUESTED);
        return new OrderResponseDto(order);
    }

    private ItemResponseDto findItemById(Long itemId) {
        return itemServiceClient.findItemById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
    }

    private UserResponseDto findUserById(Long userId) {
        return userServiceClient.findUserById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
    }
}
