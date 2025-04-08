package com.example.junggoheaven.domain.user.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePasswordRequestDto {
	@NotBlank(message = "현재 비밀번호를 입력해주세요.")
	private String oldPassword;
	@NotBlank(message = "새로운 비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&]).{8,}$"
		, message = "대소문자, 숫자, 특수문자(!,@,#,$,%,^,&)를 포함한 8자리 이상으로 입력해주세요.")
	private String newPassword;
}
