package com.example.junggoheaven.domain.bespokeinfo.dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBespokeInfoRequestDto {

	@NotNull(message = "성별을 선택해 주세요")
	@Min(value = 0) // 남성
	@Max(value = 1) // 여성
	private final Long gender;

	// 0 서울 1 경기도(인천) 2 충청도(대전세종) 3 전라도(광주) 4 경상도(대구울산부산) 5 강원도 6 제주도
	@NotNull(message = "지역을 선택해 주세요")
	@Min(value = 0)
	@Max(value = 6)
	private final Long location;

	// 0 10대이하 1 10대 2 20대 3 30대 4 40대 5 50대 6 60대
	@NotNull(message = "연령대를 선택해 주세요")
	@Min(value = 0)
	@Max(value = 6)
	private final Long ageGroup;


}
