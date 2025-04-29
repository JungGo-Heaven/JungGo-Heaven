package com.example.junggoheaven.domain.bespokeinfo.dto.response;

import com.example.junggoheaven.domain.bespokeinfo.dto.request.UserBespokeInfoRequestDto;
import com.example.junggoheaven.domain.bespokeinfo.entity.UserBespokeInfo;
import com.example.junggoheaven.domain.user.dto.user.UserInfoResponseDto;
import com.example.junggoheaven.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBespokeInfoResponseDto {
	private final Long id;

	private final UserInfoResponseDto user;

	private final Long gender;

	private final Long location;

	private final Long ageGroup;

	private UserBespokeInfoResponseDto(UserBespokeInfo userBespokeInfo) {
		this.id = userBespokeInfo.getId();
		this.user = UserInfoResponseDto.fromUser(userBespokeInfo.getUser());
		this.gender = userBespokeInfo.getGender();
		this.location = userBespokeInfo.getLocation();
		this.ageGroup = userBespokeInfo.getAgeGroup();
	}

	// static factory method
	public static UserBespokeInfoResponseDto of(UserBespokeInfo userBespokeInfo) {
		return new UserBespokeInfoResponseDto(userBespokeInfo);
	}



}
