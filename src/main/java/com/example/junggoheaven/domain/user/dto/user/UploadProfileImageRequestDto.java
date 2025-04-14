package com.example.junggoheaven.domain.user.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UploadProfileImageRequestDto {
	@NotNull(message = "등록하려는 이미지를 넣어주세요.")
	private Long id;
}
