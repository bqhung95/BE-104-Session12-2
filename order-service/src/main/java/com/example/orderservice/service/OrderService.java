package com.example.orderservice.service;

import com.example.commonevents.OrderCreatedEvent;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher publisher;

    @Transactional
    public String createOrder(OrderRequest request) {
        long start = System.currentTimeMillis();

        Order order = Order.builder()
                .customerEmail(request.getCustomerEmail())
                .customerName(request.getCustomerName())
                .totalAmount(request.getTotalAmount())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .customerEmail(order.getCustomerEmail())
                .customerName(order.getCustomerName())
                .items(request.getItems())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .build();
        publisher.publish(event);

        log.info(">>> Đơn {} tạo trong {}ms", order.getId(),
                System.currentTimeMillis() - start);
        return "Đặt hàng thành công! Mã đơn: " + order.getId();
    }
}