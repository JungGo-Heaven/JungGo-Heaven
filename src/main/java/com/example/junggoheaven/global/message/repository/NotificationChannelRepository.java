package com.example.junggoheaven.global.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.junggoheaven.global.message.entity.NotificationChannel;

public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Long> {
}
