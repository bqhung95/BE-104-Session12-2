package com.example.commonevents;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent implements Serializable {

    private Long orderId;
    private String customerEmail;
    private String customerName;
    private List<Item> items;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

    // Inner class Item cho danh sách sản phẩm
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Item implements Serializable {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
    }
}