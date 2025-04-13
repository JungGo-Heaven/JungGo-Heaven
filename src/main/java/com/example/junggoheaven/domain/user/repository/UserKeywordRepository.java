package com.example.junggoheaven.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.junggoheaven.domain.user.entity.UserKeyword;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long>, UserKeywordCustomRepository {
	Long countByUserId(Long userId);
}
