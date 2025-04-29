package com.example.junggoheaven.domain.user.dto.user;

import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;
import lombok.Getter;


@Getter
public class UserInfoResponseDto {
	// FIXME: 다른 사람이 확인하는 유저 정보 <추후 논의 필요>
	// 250417 1차 논의

	private Long id;

	private String name;

	private String email;


	private UserInfoResponseDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
	}

	// 정적 팩토리 메서드
	public static UserInfoResponseDto fromProduct(Product product) {
		User user = product.getUser();

		return new UserInfoResponseDto(user);
	}

	public static UserInfoResponseDto fromUser(User user) {
		return new UserInfoResponseDto(user);
	}

}
