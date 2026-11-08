package com.example.inventoryservice.listener;

import com.example.commonevents.OrderCreatedEvent;
import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryConsumer {

    private final InventoryRepository inventoryRepository;

    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    @Transactional
    public void handle(OrderCreatedEvent event) {
        log.info("📦 [INVENTORY] Nhận orderId={}", event.getOrderId());
        try {
            for (OrderCreatedEvent.Item item : event.getItems()) {
                Inventory inv = inventoryRepository.findById(item.getProductId())
                        .orElseThrow(() -> new RuntimeException(
                                "Sản phẩm không tồn tại: " + item.getProductId()));

                if (inv.getQuantity() < item.getQuantity()) {
                    throw new RuntimeException("Không đủ hàng cho product " + item.getProductId());
                }
                inv.setQuantity(inv.getQuantity() - item.getQuantity());
                inventoryRepository.save(inv);
                log.info("📦 Đã trừ {} cho productId={} (còn {})",
                        item.getQuantity(), item.getProductId(), inv.getQuantity());
            }
            log.info("📦 Hoàn tất trừ kho cho đơn {}", event.getOrderId());
        } catch (Exception e) {
            log.error("📦 Lỗi xử lý order {}: {}", event.getOrderId(), e.getMessage());
            throw e;
        }
    }
}
