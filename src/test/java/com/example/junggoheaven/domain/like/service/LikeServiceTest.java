package com.example.junggoheaven.domain.like.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.like.dto.LikeProductResponseDto;
import com.example.junggoheaven.domain.like.dto.LikeRequestDto;
import com.example.junggoheaven.domain.like.dto.LikeResponseDto;
import com.example.junggoheaven.domain.like.entity.Like;
import com.example.junggoheaven.domain.like.exception.AlreadyLikesException;
import com.example.junggoheaven.domain.like.exception.LikeDeletionException;
import com.example.junggoheaven.domain.like.service.component.LikeFinder;
import com.example.junggoheaven.domain.like.service.component.LikeWriter;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.repository.ProductRepository;
import com.example.junggoheaven.domain.product.service.component.ProductFinder;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

	@InjectMocks
	private LikeService likeService;

	@Mock
	private UserFinder userFinder;
	@Mock
	private ProductFinder productFinder;
	@Mock
	private LikeFinder likeFinder;
	@Mock
	private LikeWriter likeWriter;

	User user;
	Product product;
	Like like;

	@BeforeEach
	void setUp() {
		user = User.of("email", "password", "name", "010-0000-0001", "address");
		ReflectionTestUtils.setField(user, "id", 1L);
		ReflectionTestUtils.setField(user, "createdAt", LocalDateTime.of(2024,12,31,12,0,0));
		ReflectionTestUtils.setField(user, "modifiedAt", LocalDateTime.of(2024,12,31,12,0,0));

		product = new Product(user, "product", "description", 13000L);
		ReflectionTestUtils.setField(product, "id", 11L);
		ReflectionTestUtils.setField(product, "createdAt", LocalDateTime.of(2025,1,31,12,0,0));
		ReflectionTestUtils.setField(product, "modifiedAt", LocalDateTime.of(2025,1,31,12,0,0));

		like = Like.of(product, user);
		ReflectionTestUtils.setField(like, "id", 111L);
	}

	@Test
	void createLike() {
		LikeRequestDto requestDto = new  LikeRequestDto(11L);

		given(userFinder.findByUserId(any())).willReturn(user);
		given(productFinder.findProductById(any())).willReturn(product);
		given(likeFinder.getProductLikesCount(any())).willReturn(3);

		LikeResponseDto responseDto = likeService.createLike(1L, requestDto);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getProductId()).isEqualTo(product.getId());
	}

	@Test
	void createLike_unique_중복_에러_발생() {
		LikeRequestDto requestDto = new  LikeRequestDto(11L);

		given(userFinder.findByUserId(any())).willReturn(user);
		given(productFinder.findProductById(any())).willReturn(product);
		given(likeWriter.saveLike(any())).willThrow(DataIntegrityViolationException.class);

		assertThrows(AlreadyLikesException.class, () ->{
			likeService.createLike(1L, requestDto);
		});
	}

	@Test
	void getMyLikeProducts() {
		Page<Like> page = new PageImpl<>(List.of(like));
		given(likeFinder.getUserLikes(any(), any())).willReturn(page);
		Page<LikeProductResponseDto> responseDto = likeService.getMyLikeProducts(1L, 1, 5);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getTotalElements()).isEqualTo(1);
		assertThat(responseDto.getContent().get(0).getProductId()).isEqualTo(product.getId());
	}

	@Test
	void getProductLikes() {
		given(likeFinder.getProductLikesCount(any())).willReturn(3);
		given(productFinder.findProductById(any())).willReturn(product);

		LikeResponseDto responseDto = likeService.getProductLikes(11L);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getProductId()).isEqualTo(product.getId());
		assertThat(responseDto.getCount()).isEqualTo(3);
	}

	@Test
	void getPopularProducts() {
		List<Long> ids = List.of(10L);
		given(likeFinder.getProductLikesTop5()).willReturn(ids);
		given(productFinder.findLikeTop5Products(any())).willReturn(List.of(product));

		List<LikeProductResponseDto> responseDto = likeService.getPopularProducts();

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.get(0).getProductId()).isEqualTo(product.getId());
	}

	@Test
	void deleteProductLikes() {
		given(userFinder.findByUserId(any())).willReturn(user);
		given(likeFinder.getLike(any())).willReturn(like);

		likeService.deleteProductLikes(1L, 111L);

		assertThat(like.getUser()).isEqualTo(user);
	}

	@Test
	void deleteProductLikes_사용자_불일치() {
		User otheruser = User.of("email", "password", "name", "010-0000-0001", "address");
		given(userFinder.findByUserId(any())).willReturn(otheruser);
		given(likeFinder.getLike(any())).willReturn(like);

		assertThat(like.getUser()).isNotEqualTo(otheruser);
		assertThrows(LikeDeletionException.class, () ->{
			likeService.deleteProductLikes(1L, 111L);
		});
	}

	@Test
	void getProductsOfUser() {
		Page<Like> page = new PageImpl<>(List.of(like));
		given(likeFinder.getUserLikes(any(), any())).willReturn(page);

		Page<LikeProductResponseDto> responseDto = likeService.getProductsOfUser(1L, 0, 5);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getTotalElements()).isEqualTo(1);
	}
}