package com.example.junggoheaven.domain.bespokeinfo.dto.response;


import com.example.junggoheaven.domain.user.dto.user.UserInfoResponseDto;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBespokeAgreeResponseDto {
	private final UserInfoResponseDto user;

	private final Boolean bespokeAgree;


	private UserBespokeAgreeResponseDto(User user) {
		this.user = UserInfoResponseDto.fromUser(user);
		this.bespokeAgree = user.getBespokeAgree();
	}


	public static UserBespokeAgreeResponseDto of(User user) {
		return new UserBespokeAgreeResponseDto(user);
	}


}
