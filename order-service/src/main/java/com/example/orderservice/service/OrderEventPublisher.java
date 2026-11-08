package com.example.orderservice.service;

import com.example.commonevents.OrderCreatedEvent;
import com.example.orderservice.config.KafkaTopicConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(OrderCreatedEvent event) {
        CompletableFuture<?> future = kafkaTemplate.send(
                KafkaTopicConfig.ORDER_TOPIC, String.valueOf(event.getOrderId()), event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("❌ Gửi event thất bại orderId={}: {}", event.getOrderId(), ex.getMessage());
            } else {
                log.info("✅ Event sent orderId={}", event.getOrderId());
            }
        });
    }
}
