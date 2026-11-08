package com.example.emailservice.listener;

import com.example.commonevents.OrderCreatedEvent;
import com.example.emailservice.service.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    private final EmailSender emailSender;

    @KafkaListener(topics = "order-events", groupId = "email-group")
    public void handle(OrderCreatedEvent event) throws InterruptedException {
        log.info("📧 [EMAIL] Nhận orderId={} của {}", event.getOrderId(), event.getCustomerEmail());
        try {
            emailSender.send(
                    event.getCustomerEmail(),
                    "Xác nhận đơn hàng #" + event.getOrderId(),
                    "Cảm ơn bạn! Tổng tiền: " + event.getTotalAmount()
            );
            log.info("📧 Cộng 10 điểm thành viên cho {}", event.getCustomerName());
        } catch (Exception e) {
            log.error("📧 Lỗi xử lý order {}: {}", event.getOrderId(), e.getMessage());
            throw e;
        }
    }
}
