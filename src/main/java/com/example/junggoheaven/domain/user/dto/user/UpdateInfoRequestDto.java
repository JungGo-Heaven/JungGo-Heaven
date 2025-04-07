package com.example.junggoheaven.domain.user.dto.user;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateInfoRequestDto {
	private String name;
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 양식에 맞지 않습니다. ex) 010-1234-5678")
	private String phoneNumber;
	private String address;
}
