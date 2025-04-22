package com.example.junggoheaven.global.common.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.junggoheaven.global.common.response.ResponseDto;
import com.example.junggoheaven.global.common.response.ValidResponseDto;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ValidResponseDto>> invalidRequestExceptionException(
		MethodArgumentNotValidException ex) {
		List<ValidResponseDto> errors = new ArrayList<>();

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		for (FieldError err : fieldErrors) {
			errors.add(ValidResponseDto.of(err.getField(), err.getDefaultMessage()));
		}

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ResponseDto> handleBaseException(BaseException ex){
		ResponseDto error = ResponseDto.fail(ex.getStatus(), ex.getErrorCode().getCode(), ex.getMessage());
		return new ResponseEntity<>(error, ex.getStatus());
	}

	@ExceptionHandler(OAuth2AuthenticationException.class)
	public ResponseEntity<ResponseDto> authenticationExceptionException(OAuth2AuthenticationException ex) {
		ResponseDto error = ResponseDto.fail(HttpStatus.UNAUTHORIZED, ex.getError().getErrorCode(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	// security에서 제공하는 exception -> error code가 없어 class name으로
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
		ResponseDto error = ResponseDto.fail(HttpStatus.FORBIDDEN, ex.getClass().getSimpleName(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> handleException(Exception ex){
		ResponseDto error = ResponseDto.fail(HttpStatus.BAD_REQUEST, ex.getClass().getSimpleName(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
