package com.example.junggoheaven.domain.inquiry.dto.request;

import com.example.junggoheaven.domain.inquiry.eunms.InquiryStatus;
import com.example.junggoheaven.global.common.annotation.ValidEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InquiryStatusRequestDto {
	@NotBlank(message = "변경하려는 문의 id를 입력해주세요.")
	private Long inquiryId;
	@NotBlank(message = "변경하려는 상태 코드를 입력해주세요.")
	@ValidEnum(enumClass = InquiryStatus.class, message = "WAITING, COMPLETED, DELETED 중 하나만 입력 가능합니다.")
	private InquiryStatus status;
}