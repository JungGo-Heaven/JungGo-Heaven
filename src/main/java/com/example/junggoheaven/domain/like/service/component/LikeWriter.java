package com.example.junggoheaven.domain.like.service.component;

import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.like.entity.Like;
import com.example.junggoheaven.domain.like.repository.LikeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeWriter {

	private final LikeRepository likeRepository;

	public Like saveLike(Like like){
		return likeRepository.save(like);
	}

	public void deleteLike(Like like){
		likeRepository.delete(like);
	}
}
