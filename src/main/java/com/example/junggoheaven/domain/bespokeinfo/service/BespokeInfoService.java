package com.example.junggoheaven.domain.bespokeinfo.service;

import com.example.junggoheaven.domain.bespokeinfo.dto.request.UserBespokeAgreeRequestDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.request.UserBespokeInfoRequestDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.response.UserBespokeAgreeResponseDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.response.UserBespokeInfoResponseDto;
import com.example.junggoheaven.domain.bespokeinfo.entity.UserBespokeInfo;
import com.example.junggoheaven.domain.bespokeinfo.exception.NotAgreeException;
import com.example.junggoheaven.domain.bespokeinfo.exception.SameAgreeException;
import com.example.junggoheaven.domain.bespokeinfo.repository.ProductBespokeInfoRepository;
import com.example.junggoheaven.domain.bespokeinfo.repository.UserBespokeInfoRepository;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.repository.UserRepository;
import com.example.junggoheaven.domain.user.service.UserService;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserWriter;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BespokeInfoService {

	private final ProductBespokeInfoRepository productBespokeInfoRepository;
	private final UserBespokeInfoRepository userBespokeInfoRepository;

	private final UserFinder userFinder;
	private final UserWriter userWriter;


	/*
		맞춤 정보 동의 여부 판단 메서드
	*/
	@Transactional
	public UserBespokeAgreeResponseDto isUserBespokeAgree(AuthUser authUser) {
		User user = userFinder.findByUserId(authUser.getId());

		return UserBespokeAgreeResponseDto.of(user);

	}


	/*
		사용자 맞춤정보 동의 메서드
	*/
	@Transactional
	public UserBespokeAgreeResponseDto setBespokeAgree(AuthUser authUser,
		UserBespokeAgreeRequestDto userBespokeAgreeRequestDto) {

		User user = userFinder.findByUserId(authUser.getId());

		boolean userBespokeAgree = user.getBespokeAgree();

		// 맞춤 정보 동의가 기존의 값과 동일할 경우
		if (userBespokeAgree == userBespokeAgreeRequestDto.getBespokeAgree()) {
			throw new SameAgreeException();
		}

		user.updateBespokeAgree(userBespokeAgreeRequestDto.getBespokeAgree());

		userWriter.saveUser(user);

		return UserBespokeAgreeResponseDto.of(user);

	}


	/*
		사용자 맞춤정보 동의시 정보 생성
	*/
	@Transactional
	public UserBespokeInfoResponseDto setBespokeInfo(AuthUser authUser,
		UserBespokeInfoRequestDto userBespokeInfoRequestDto) {

		User user = userFinder.findByUserId(authUser.getId());

		// 사용자 맞춤 정보에 동의하지 않았을 경우
		if (user.getBespokeAgree() == false) {
			throw new NotAgreeException();
		}

		UserBespokeInfo userBespokeInfo = UserBespokeInfo.of(user, userBespokeInfoRequestDto);

		userBespokeInfoRepository.save(userBespokeInfo);

		return UserBespokeInfoResponseDto.of(userBespokeInfo);

	}


	/*
		맞춤 정보 수정
	*/
	@Transactional
	public UserBespokeInfoResponseDto updateBespokeInfo(AuthUser authUser,
		UserBespokeInfoRequestDto userBespokeInfoRequestDto) {

		User user = userFinder.findByUserId(authUser.getId());

		if (user.getBespokeAgree() == false) {
			throw new NotAgreeException();
		}

		UserBespokeInfo userBespokeInfo = userBespokeInfoRepository.findByUsersId(authUser.getId());

		userBespokeInfo.updateUserBespokeInfo(userBespokeInfoRequestDto);

		userBespokeInfoRepository.save(userBespokeInfo);

		//userBespokeInfoRepository.findByUserId()
		return UserBespokeInfoResponseDto.of(userBespokeInfo);

	}




	// product service 부분
	/*
		상품 등록시 로그 저장
	*/
	@Transactional
	public void saveProductLog() {

	}







	/*
	사용자의 맞춤정보 동의 여부 판단
	다른 서비스를 위한 매서드
	*/
	@Transactional
	public boolean isBespokeAgree(AuthUser authUser) {
		User user = userFinder.findByUserId(authUser.getId());

		if (user.getBespokeAgree() == true) {
			return true;
		} else {
			return false;
		}

	}





}
