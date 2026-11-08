package com.example.emailservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSender {

    /** Giả lập gửi email (thực tế dùng JavaMailSender) */
    public void send(String to, String subject, String body) throws InterruptedException {
        Thread.sleep(1000);   // giả lập delay mạng
        log.info("📧 Đã gửi email tới {} | Subject: {} | Body: {}", to, subject, body);
    }
}
