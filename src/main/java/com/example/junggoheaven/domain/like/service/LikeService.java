package com.example.junggoheaven.domain.like.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final ProductFinder productFinder;
	private final UserFinder userFinder;
	private final LikeFinder likeFinder;
	private final LikeWriter likeWriter;

	public LikeResponseDto createLike(Long userId, LikeRequestDto requestDto) {
		User user = userFinder.findByUserId(userId);
		Long productId = requestDto.getProductId();
		Product product = productFinder.findProductById(productId);

		Like newLike = Like.of(product, user);
		try {
			likeWriter.saveLike(newLike);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyLikesException();
		}

		int count = likeFinder.getProductLikesCount(productId);
		return LikeResponseDto.from(newLike, count);
	}

	public Page<LikeProductResponseDto> getMyLikeProducts(Long userId, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Like> userLikes = likeFinder.getUserLikes(userId, pageable);
		return userLikes.map(LikeProductResponseDto::from);
	}

	public LikeResponseDto getProductLikes(Long productId) {
		int count = likeFinder.getProductLikesCount(productId);
		Product product = productFinder.findProductById(productId);
		return LikeResponseDto.from(product, count);
	}

	public List<LikeProductResponseDto> getPopularProducts() {
		List<Long> top5ProductIds = likeFinder.getProductLikesTop5();
		List<Product> products = productFinder.findLikeTop5Products(top5ProductIds);
		return products.stream().map(LikeProductResponseDto::from).toList();
	}

	public void deleteProductLikes(Long userId, Long likeId) {
		User user = userFinder.findByUserId(userId);
		Like like = likeFinder.getLike(likeId);

		if (!like.getUser().equals(user)) {
			throw new LikeDeletionException();
		}

		likeWriter.deleteLike(like);
	}

	public Page<LikeProductResponseDto> getProductsOfUser(Long userId, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return likeFinder.getUserLikes(userId, pageable).map(LikeProductResponseDto::from);
	}
}
