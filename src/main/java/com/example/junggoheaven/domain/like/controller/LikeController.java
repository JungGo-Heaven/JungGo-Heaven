package com.example.junggoheaven.domain.like.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.domain.like.dto.LikeProductResponseDto;
import com.example.junggoheaven.domain.like.dto.LikeRequestDto;
import com.example.junggoheaven.domain.like.dto.LikeResponseDto;
import com.example.junggoheaven.domain.like.service.LikeService;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/likes")
	public ResponseDto<LikeResponseDto> createLike(@AuthenticationPrincipal AuthUser authUser,
		@RequestBody LikeRequestDto requestDto) {
		return ResponseDto.success(likeService.createLike(authUser.getId(), requestDto));
	}

	@GetMapping("/likes/my")
	public ResponseDto<Page<LikeProductResponseDto>> getMyLikeProducts(@AuthenticationPrincipal AuthUser authUser,
		@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
		return ResponseDto.success(likeService.getMyLikeProducts(authUser.getId(), pageNumber, pageSize));
	}

	@GetMapping("/likes/products/{productId}")
	public ResponseDto<LikeResponseDto> getProductLikes(@PathVariable Long productId) {
		return ResponseDto.success(likeService.getProductLikes(productId));
	}

	@GetMapping("/likes/best")
	public ResponseDto<List<LikeProductResponseDto>> getPopularProducts() {
		return ResponseDto.success(likeService.getPopularProducts());
	}

	@DeleteMapping("/likes/{likeId}}")
	public ResponseDto<Void> deleteProductLikes(@AuthenticationPrincipal AuthUser authUser,
		@PathVariable Long likeId) {
		likeService.deleteProductLikes(authUser.getId(), likeId);
		return ResponseDto.success(null);
	}

	@Secured(UserRole.Authority.ADMIN)
	@GetMapping("/likes/users/{userId}")
	public ResponseDto<Page<LikeProductResponseDto>> getProductsOfUser(@PathVariable Long userId,
		@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
		return ResponseDto.success(likeService.getProductsOfUser(userId, pageNumber, pageSize));
	}
}
