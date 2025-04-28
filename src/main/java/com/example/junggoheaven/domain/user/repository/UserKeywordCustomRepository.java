package com.example.junggoheaven.domain.user.repository;

import java.util.List;

import com.example.junggoheaven.global.message.dto.MatchedUserDto;

public interface UserKeywordCustomRepository {
	List<MatchedUserDto> findAllUserIdByProductName(String productName);
	MatchedUserDto findMatchedUserDtoById(Long userId);
}
