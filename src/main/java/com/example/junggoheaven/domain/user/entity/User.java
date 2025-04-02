package com.example.junggoheaven.domain.user.entity;

import java.time.LocalDateTime;

import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.global.common.entity.TimeStamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String email;
	private String password;
	private String name;
	private String phoneNumber;
	private String address;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	private LocalDateTime deletedAt;

	@Builder
	public User(String email, String password, String name, String phoneNumber, String address) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = UserRole.ROLE_USER;
		this.status = UserStatus.ACTIVE;
	}
}
