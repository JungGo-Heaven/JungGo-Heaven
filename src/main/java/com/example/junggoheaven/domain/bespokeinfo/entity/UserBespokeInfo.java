package com.example.junggoheaven.domain.bespokeinfo.entity;


import com.example.junggoheaven.domain.bespokeinfo.dto.request.UserBespokeInfoRequestDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name ="users_bespoke_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBespokeInfo extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "users_id")
	private User user;

	@Column(nullable = false)
	private Long gender;

	@Column(nullable = false)
	private Long location;

	@Column(nullable = false)
	private Long ageGroup;

	private UserBespokeInfo(User user, UserBespokeInfoRequestDto userBespokeInfoRequestDto) {
		this.user = user;
		this.gender = userBespokeInfoRequestDto.getGender();
		this.location = userBespokeInfoRequestDto.getLocation();
		this.ageGroup = userBespokeInfoRequestDto.getAgeGroup();
	}

	// static factory method
	public static UserBespokeInfo of(User user, UserBespokeInfoRequestDto userBespokeInfoRequestDto) {
		return new UserBespokeInfo(user, userBespokeInfoRequestDto);
	}


	public void updateUserBespokeInfo(UserBespokeInfoRequestDto userBespokeInfoRequestDto) {
		this.gender = userBespokeInfoRequestDto.getGender();
		this.location = userBespokeInfoRequestDto.getLocation();
		this.ageGroup = userBespokeInfoRequestDto.getAgeGroup();
	}

}
