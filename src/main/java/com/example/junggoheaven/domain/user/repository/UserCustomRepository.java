package com.example.junggoheaven.domain.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.message.dto.MatchedUserDto;

public interface UserCustomRepository {
	Page<User> findAllByStatusAndEmail(String status, String email, Pageable pageable);
	MatchedUserDto findMatchedUserDtoById(Long userId);
}
