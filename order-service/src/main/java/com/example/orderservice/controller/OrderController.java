package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody OrderRequest request) {
        long start = System.currentTimeMillis();
        String result = orderService.createOrder(request);
        return ResponseEntity.ok()
                .header("X-Response-Time-ms",
                        String.valueOf(System.currentTimeMillis() - start))
                .body(result);
    }
}
