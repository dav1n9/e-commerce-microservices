package com.example.orderservice.domain.messagequeue;

import com.example.orderservice.domain.order.dto.OrderResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderResponseDto send(String topic, OrderResponseDto orderDto){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            // OrderDto 객체를 JSON 문자열로 직렬화
            jsonInString = mapper.writeValueAsString(orderDto);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);

        log.info("Kafka Producer send data " + orderDto);

        return orderDto;
    }
}
