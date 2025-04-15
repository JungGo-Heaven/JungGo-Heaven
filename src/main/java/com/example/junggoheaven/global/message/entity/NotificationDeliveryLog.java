package com.example.junggoheaven.global.message.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.example.junggoheaven.global.common.entity.TimeStamp;
import com.example.junggoheaven.global.message.enums.ChannelType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class NotificationDeliveryLog extends TimeStamp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ChannelType channelType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ErrorMessage errorMessage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_event_log_id", nullable = false)
	NotificationEventLog notificationEventLog;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Builder
	public NotificationDeliveryLog(ChannelType channelType, Status status, ErrorMessage errorMessage, NotificationEventLog notificationEventLog) {
		this.channelType = channelType;
		this.status = status;
		this.errorMessage = errorMessage;
		this.notificationEventLog = notificationEventLog;
	}

	public enum Status {
		SUCCESS, FAIL
	}

	public enum ErrorMessage {
		NULL, TIME_OUT
	}
}
