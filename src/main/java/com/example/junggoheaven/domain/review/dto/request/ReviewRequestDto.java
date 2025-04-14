package com.example.junggoheaven.domain.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {

    @NotNull
    @Min(value = 1, message = "최소 1점부터 설정 가능합니다.")
    @Max(value = 5,  message = "최대 5점까지 설정 가능합니다.")
    private Byte rating;

    @NotBlank
    @Size(min = 5, max = 200, message = "리뷰를 5~200자 작성해주세요")
    private String comment;
}
