package com.example.junggoheaven.global.message.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.junggoheaven.global.message.entity.NotificationChannel;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationChannelBulkRepository {
	private final JdbcTemplate jdbcTemplate;

	private static final int BATCH_SIZE = 1000;
	public void insert(List<NotificationChannel> notificationChannels) {
		String sql = "INSERT INTO notification_channel (user_id, channel_type, token) VALUES (?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, notificationChannels, BATCH_SIZE, (ps, notificationChannel) -> {
			ps.setLong(1, notificationChannel.getUser().getId());
			ps.setString(2, notificationChannel.getChannelType().name());
			ps.setString(3, notificationChannel.getToken());
		});
	}
}
