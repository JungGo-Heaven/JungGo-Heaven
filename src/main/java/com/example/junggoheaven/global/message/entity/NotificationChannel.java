package com.example.junggoheaven.global.message.entity;

import com.example.junggoheaven.domain.user.entity.User;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class NotificationChannel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ChannelType channelType;

	@Column(nullable = false)
	private String token;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private NotificationChannel (ChannelType channelType, String token, User user) {
		this.channelType = channelType;
		this.token = token;
		this.user = user;
	}

	public static NotificationChannel of(ChannelType channelType, String token, User user) {
		return new NotificationChannel(channelType, token, user);
	}
}
