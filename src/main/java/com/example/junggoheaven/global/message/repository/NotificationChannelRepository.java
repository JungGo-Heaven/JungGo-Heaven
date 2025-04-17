package com.example.junggoheaven.global.message.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.junggoheaven.global.message.entity.NotificationChannel;

public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Long> {
	@Query("SELECT n FROM NotificationChannel n WHERE n.user.id = :userId")
	List<NotificationChannel> findByUserId(Long userId);
}
