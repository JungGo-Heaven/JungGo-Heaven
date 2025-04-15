package com.example.junggoheaven.global.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.junggoheaven.global.message.entity.NotificationEventLog;

public interface NotificationEventLogRepository extends JpaRepository<NotificationEventLog, Long> {
}
