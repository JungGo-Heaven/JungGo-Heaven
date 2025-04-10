package com.example.junggoheaven.domain.like.service.component;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.like.entity.Like;
import com.example.junggoheaven.domain.like.exception.InvalidLikesException;
import com.example.junggoheaven.domain.like.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeFinder {

	private final LikeRepository likeRepository;

	public int getProductLikesCount(Long productId) {
		return likeRepository.countByProduct_Id(productId);
	}

	public Page<Like> getUserLikes(Long userId, Pageable pageable) {
		return likeRepository.findByUserAndValidProductOrderByIdAsc(userId, pageable);
	}

	public Like getLikeByProductId(Long productId) {
		return likeRepository.findByValidProductId(productId);
	}

	public List<Long> getProductLikesTop5() {
		return likeRepository.findTop5Likes();
	}

	public Like getLike(Long likeId) {
		return likeRepository.findValidById(likeId).orElseThrow(InvalidLikesException::new);
	}
}
