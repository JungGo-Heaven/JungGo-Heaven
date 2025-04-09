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

import com.example.junggoheaven.global.common.response.ErrorResponseDto;
import com.example.junggoheaven.global.common.response.ValidResponseDto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final HttpServletResponse httpServletResponse;

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
	public ResponseEntity<ErrorResponseDto> handleBaseException(BaseException ex) {
		ErrorResponseDto error = ErrorResponseDto.of(ex);
		return new ResponseEntity<>(error, ex.getStatus());
	}

	@ExceptionHandler(OAuth2AuthenticationException.class)
	public ResponseEntity<ErrorResponseDto> authenticationExceptionException(
		OAuth2AuthenticationException ex) {
		ErrorResponseDto error = ErrorResponseDto.of(ex.getClass().getSimpleName(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
		ErrorResponseDto error = ErrorResponseDto.of(ex.getClass().getSimpleName(), "Invalid permission. 해당 권한이 없습니다.");
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	// FIXME: 화면에 작성 시 AuthenticationCredentialsNotFoundException으로만 잡게 됨. 문제 해결 필요함.
	// private ErrorResponseDto sendErrorResponse(Exception ex, int code) throws IOException {
	// 	httpServletResponse.setStatus(code);
	// 	httpServletResponse.setContentType("application/json;charset=UTF-8");
	//
	// 	ErrorResponseDto errorResponse = ErrorResponseDto.of(ex.getClass().getSimpleName(), ex.getMessage());
	//
	// 	ObjectMapper mapper = new ObjectMapper();
	// 	String jsonResponse = mapper.writeValueAsString(errorResponse);
	//
	// 	httpServletResponse.getWriter().write(jsonResponse);
	//
	// 	return errorResponse;
	// }
}
