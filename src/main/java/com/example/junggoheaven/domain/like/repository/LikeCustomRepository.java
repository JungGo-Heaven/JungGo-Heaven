package com.example.junggoheaven.domain.like.repository;

import java.util.List;

import com.example.junggoheaven.domain.like.entity.Like;

public interface LikeCustomRepository {
	List<Long> findTop5Likes();
}
