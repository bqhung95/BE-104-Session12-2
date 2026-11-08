package com.example.orderservice.dto;

import com.example.commonevents.OrderCreatedEvent;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private String customerEmail;
    private String customerName;
    private BigDecimal totalAmount;
    private List<OrderCreatedEvent.Item> items;
}
