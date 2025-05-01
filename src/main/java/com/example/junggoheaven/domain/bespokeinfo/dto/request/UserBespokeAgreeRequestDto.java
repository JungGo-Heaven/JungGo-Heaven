package com.example.junggoheaven.domain.bespokeinfo.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBespokeAgreeRequestDto {

	@NotBlank
	private Boolean bespokeAgree;

}
