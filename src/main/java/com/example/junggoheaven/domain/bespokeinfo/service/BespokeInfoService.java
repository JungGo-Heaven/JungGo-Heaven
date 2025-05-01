package com.example.junggoheaven.domain.bespokeinfo.service;

import com.example.junggoheaven.domain.bespokeinfo.dto.request.UserBespokeAgreeRequestDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.request.UserBespokeInfoRequestDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.response.UserBespokeAgreeResponseDto;
import com.example.junggoheaven.domain.bespokeinfo.dto.response.UserBespokeInfoResponseDto;
import com.example.junggoheaven.domain.bespokeinfo.entity.ProductBespokeInfo;
import com.example.junggoheaven.domain.bespokeinfo.entity.UserBespokeInfo;
import com.example.junggoheaven.domain.bespokeinfo.exception.NotAgreeException;
import com.example.junggoheaven.domain.bespokeinfo.exception.SameAgreeException;
import com.example.junggoheaven.domain.bespokeinfo.repository.ProductBespokeInfoRepository;
import com.example.junggoheaven.domain.bespokeinfo.repository.UserBespokeInfoRepository;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.repository.UserRepository;
import com.example.junggoheaven.domain.user.service.UserService;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserWriter;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BespokeInfoService {

	private final RestTemplate restTemplate;
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
		사용자 맞춤 정보 수정
	*/
	@Transactional
	public UserBespokeInfoResponseDto updateBespokeInfo(AuthUser authUser,
		UserBespokeInfoRequestDto userBespokeInfoRequestDto) {

		User user = userFinder.findByUserId(authUser.getId());

		if (user.getBespokeAgree() == false) {
			throw new NotAgreeException();
		}

		UserBespokeInfo userBespokeInfo = userBespokeInfoRepository.findByUsersId(user);

		userBespokeInfo.updateUserBespokeInfo(userBespokeInfoRequestDto);

		userBespokeInfoRepository.save(userBespokeInfo);

		return UserBespokeInfoResponseDto.of(userBespokeInfo);

	}


	/*
		product service 부분
		상품 맞춤정보 로그 스냅샷저장
	*/
	@Transactional
	public void saveProductLog(AuthUser authUser, Product product) {
		User user = userFinder.findByUserId(authUser.getId());

		if (user.getBespokeAgree() == false) {
			throw new NotAgreeException();
		}

		UserBespokeInfo userBespokeInfo = userBespokeInfoRepository.findByUsersId(user);

		ProductBespokeInfo productBespokeInfo = ProductBespokeInfo.of(userBespokeInfo, product);

		productBespokeInfoRepository.save(productBespokeInfo);

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


	/*
		상품추천 분석 서버 호출
	*/
	public String getBespokeProduct(AuthUser authUser) {
		String url = "http://127.0.0.1:5000/mlserver";

		User user = userFinder.findByUserId(authUser.getId());

		if (user.getBespokeAgree() == false) {
			throw new NotAgreeException();
		}

		UserBespokeInfo userBespokeInfo = userBespokeInfoRepository.findByUsersId(user);

		UserBespokeInfoRequestDto userBespokeInfoRequestDto = new UserBespokeInfoRequestDto(userBespokeInfo.getGender(),
			userBespokeInfo.getLocation(), userBespokeInfo.getAgeGroup());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserBespokeInfoRequestDto> entity = new HttpEntity<>(userBespokeInfoRequestDto, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		return response.getBody();
	}



}
