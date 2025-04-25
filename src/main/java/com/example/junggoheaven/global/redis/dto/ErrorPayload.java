package com.example.junggoheaven.global.redis.dto;

import com.example.junggoheaven.global.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorPayload {
    private ErrorCode code;
    private int status;
    private String message;
    private Long userId;
    private Map<String, Object> context;
}