package com.example.junggoheaven.global.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.junggoheaven.global.message.entity.NotificationDeliveryLog;

public interface NotificationDeliveryLogRepository extends JpaRepository<NotificationDeliveryLog, Long> {
}
