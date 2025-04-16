package com.example.junggoheaven.domain.like.service.component;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.like.entity.Like;
import com.example.junggoheaven.domain.like.exception.InvalidLikesException;
import com.example.junggoheaven.domain.like.repository.LikeRepository;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class LikeFinderTest {

	@InjectMocks
	private LikeFinder likeFinder;
	@Mock
	private LikeRepository likeRepository;

	Like one;
	Like two;
	Product product;
	User user;

	@BeforeEach
	void setUp() {
		user = User.of("emali", "name","010-1234-1234");
		ReflectionTestUtils.setField(user, "id", 1L);

		product = new Product(user, "product", "information", 13000L);
		ReflectionTestUtils.setField(product, "id", 10L);

		one = Like.of(product, user);
		two = Like.of(product, user);
		ReflectionTestUtils.setField(one, "id", 11L);
		ReflectionTestUtils.setField(two, "id", 12L);
	}

	@Test
	void getProductLikesCount() {
		int count = 5;
		given(likeRepository.countByProductId(1L)).willReturn(count);
		int result = likeFinder.getProductLikesCount(1L);
		assertThat(result).isEqualTo(count);
	}

	@Test
	void getUserLikes() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Like> page = new PageImpl<>(List.of(one, two));
		given(likeRepository.findByUserAndValidProductOrderByIdAsc(any(), any())).willReturn(page);

		Page<Like> userLikes = likeFinder.getUserLikes(1L, pageable);

		assertThat(userLikes).isNotNull();
		assertThat(userLikes.getTotalElements()).isEqualTo(2);
		assertThat(userLikes.getContent().get(0)).isEqualTo(one);
	}

	@Test
	void getLikeByProductId() {
		given(likeRepository.findByValidProductId(any())).willReturn(one);
		Like result = likeFinder.getLikeByProductId(11L);
		assertThat(result).isEqualTo(one);
		assertThat(result.getProduct()).isEqualTo(product);
	}

	@Test
	void getProductLikesTop5() {
		List<Long> list = List.of(product.getId());
		given(likeRepository.findTop5Likes()).willReturn(list);

		List<Long> ids = likeFinder.getProductLikesTop5();

		assertThat(ids).isNotNull();
		assertThat(ids.get(0)).isEqualTo(product.getId());
	}

	@Test
	void getLike() {
		given(likeRepository.findValidById(1L)).willReturn(Optional.of(one));
		given(likeRepository.findValidById(2L)).willReturn(Optional.empty());

		Like result = likeFinder.getLike(1L);
		assertThat(result).isNotNull();
		assertThat(result.getProduct()).isEqualTo(product);

		assertThrows(InvalidLikesException.class, () -> {
			likeFinder.getLike(2L);
		});
	}
}