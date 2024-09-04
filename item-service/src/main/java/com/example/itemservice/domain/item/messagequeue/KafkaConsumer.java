package com.example.itemservice.domain.item.messagequeue;

import com.example.itemservice.domain.item.entity.Item;
import com.example.itemservice.domain.item.repository.ItemRepository;
import com.example.itemservice.global.exception.BusinessException;
import com.example.itemservice.global.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ItemRepository itemRepository;

    @KafkaListener(topics = "remove-item-stock")
    public void removeStock(String kafkaMessage) {
        log.info("Kafka Message: -> " + kafkaMessage);
        processStockUpdate(kafkaMessage, Item::removeStock);
    }

    @KafkaListener(topics = "add-item-stock")
    public void addStock(String kafkaMessage) {
        log.info("Kafka Message: -> " + kafkaMessage);
        processStockUpdate(kafkaMessage, Item::addStock);
    }

    private void processStockUpdate(String kafkaMessage, BiConsumer<Item, Integer> stockOperation) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(kafkaMessage, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> orderItems = (List<Map<String, Object>>) map.get("orderItems");

            for (Map<String, Object> orderItem : orderItems) {
                Long itemId = ((Integer) orderItem.get("itemId")).longValue();
                Integer count = (Integer) orderItem.get("count");

                Item item = itemRepository.findById(itemId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));

                stockOperation.accept(item, count);
                itemRepository.save(item);
            }

        } catch (JsonProcessingException ex) {
            log.error("Failed to parse Kafka message", ex);
        }
    }
}