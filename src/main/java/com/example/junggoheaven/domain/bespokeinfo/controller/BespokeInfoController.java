package com.example.junggoheaven.domain.bespokeinfo.controller;


import com.example.junggoheaven.domain.bespokeinfo.dto.request.UserBespokeAgreeRequestDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.request.UserBespokeInfoRequestDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.response.UserBespokeAgreeResponseDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.response.UserBespokeInfoResponseDto;
import com.example.junggoheaven.domain.bespokeinfo.service.BespokeInfoService;
import com.example.junggoheaven.domain.user.repository.UserRepository;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class BespokeInfoController {
	private final BespokeInfoService bespokeInfoService;


	/*
		맞춤 정보 동의 여부 조회
	*/
	@GetMapping("/v1/bespokes/agree")
	public ResponseDto<UserBespokeAgreeResponseDto> isBespokeAgree(
		@AuthenticationPrincipal AuthUser authUser) {

		UserBespokeAgreeResponseDto userBespokeAgreeResponseDto = bespokeInfoService.isUserBespokeAgree(authUser);

		return ResponseDto.success(userBespokeAgreeResponseDto);
	}



	/*
		맞춤 정보 동의
	*/
	@PatchMapping("/v1/bespokes/agree")
	public ResponseDto<UserBespokeAgreeResponseDto> setBespokeAgree(
		@AuthenticationPrincipal AuthUser authUser,
		@RequestBody UserBespokeAgreeRequestDto userBespokeAgreeRequestDto
		) {

		UserBespokeAgreeResponseDto userBespokeAgreeResponseDto = bespokeInfoService.setBespokeAgree(authUser, userBespokeAgreeRequestDto);

		return ResponseDto.success(userBespokeAgreeResponseDto);


	}



	/*
		사용자 맞춤 정보 설정
	*/
	@PostMapping("/v1/bespokes")
	public ResponseDto<UserBespokeInfoResponseDto> setBespokeInfo(
		@AuthenticationPrincipal AuthUser authUser,
		@Valid @RequestBody UserBespokeInfoRequestDto userBespokeInfoRequestDto
		) {
		UserBespokeInfoResponseDto userBespokeInfoResponseDto = bespokeInfoService.setBespokeInfo(authUser, userBespokeInfoRequestDto);

		return ResponseDto.success(userBespokeInfoResponseDto);
	}


	/*
		사용자 맞춤 정보 수정
	*/
	@PatchMapping("/v1/bespokes")
	public ResponseDto<UserBespokeInfoResponseDto> updateBespokeInfo(@AuthenticationPrincipal AuthUser authUser,
		@RequestBody UserBespokeInfoRequestDto userBespokeInfoRequestDto) {
		UserBespokeInfoResponseDto userBespokeInfoResponseDto = bespokeInfoService.updateBespokeInfo(authUser, userBespokeInfoRequestDto);

		return ResponseDto.success(userBespokeInfoResponseDto);
	}






}
