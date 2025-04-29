package com.example.junggoheaven.domain.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.example.junggoheaven.domain.image.entity.ProfileImage;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.global.common.entity.TimeStamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET status = 'DELETED', deleted_at = NOW() WHERE id = ?")
@FilterDef(name = "deletedFilter")
@Filter(name = "deletedFilter", condition = "status <> 'DELETED'")
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
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_image_id")
	private ProfileImage profileImage;
	private Boolean bespokeAgree;

	@Builder
	private User(String email, String password, String name, String phoneNumber, String address) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = UserRole.ROLE_USER;
		this.status = UserStatus.ACTIVE;
		this.bespokeAgree = false;
	}

	@Builder
	private User(String email, String name, String phoneNumber) {
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = UserRole.ROLE_GUEST;
		this.status = UserStatus.ACTIVE;
		this.bespokeAgree = false;
	}

	public static User of(String email, String password, String name, String phoneNumber, String address) {
		return User.builder()
			.email(email).password(password).name(name).phoneNumber(phoneNumber).address(address).build();
	}

	public static User of(String email, String name, String phoneNumber){
		return User.builder()
			.email(email).name(name).phoneNumber(phoneNumber).build();
	}

	public void updateStatus(UserStatus status) {
		this.status = status;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void guestAddInfo(String password, String phoneNumber, String address) {
		this.password = password;
		if (phoneNumber != null && !phoneNumber.isBlank()) {
			this.phoneNumber = phoneNumber;
		}
		this.address = address;
		this.role = UserRole.ROLE_USER;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updatePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void updateAddress(String address) {
		this.address = address;
	}

	public void updateProfileImage(ProfileImage profileImage) {
		this.profileImage = profileImage;
	}

	public void updateBespokeAgree(boolean bespokeAgree) { this.bespokeAgree = bespokeAgree; }
}
