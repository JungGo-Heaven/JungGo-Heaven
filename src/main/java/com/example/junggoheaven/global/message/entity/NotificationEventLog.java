package com.example.junggoheaven.global.message.entity;

import com.example.junggoheaven.global.common.entity.TimeStamp;
import com.example.junggoheaven.global.message.enums.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class NotificationEventLog extends TimeStamp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationType notificationType;

	@Column(nullable = false)
	private String message;

	@Builder
	public NotificationEventLog(Long userId, NotificationType notificationType, String message) {
		this.userId = userId;
		this.notificationType = notificationType;
		this.message = message;
	}
}
