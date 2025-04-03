package com.example.junggoheaven.global.auth.dto.reqeust;

import com.example.junggoheaven.domain.user.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

	@Email(message = "이메일 양식에 맞지 않습니다.")
	@NotBlank(message = "이메일 입력은 필수 입니다.")
	private String email;

	@NotBlank(message = "비밀번호 입력은 필수 입니다.")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&]).{8,}$"
		, message = "대소문자, 숫자, 특수문자(!,@,#,$,%,^,&)를 포함한 8자리 이상으로 입력해주세요.")
	private String password;

	@NotBlank(message = "이름 입력은 필수 입니다.")
	private String name;

	@NotBlank(message = "전화번호 입력은 필수 입니다.")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 양식에 맞지 않습니다. ex) 010-1234-5678")
	private String phoneNumber;

	@NotBlank(message = "주소 입력은 필수 입니다.")
	private String address;
}
