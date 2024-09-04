package com.example.itemservice.domain.item.repository;

import com.example.itemservice.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
