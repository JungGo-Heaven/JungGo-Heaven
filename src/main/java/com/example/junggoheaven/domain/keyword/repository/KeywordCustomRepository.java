package com.example.junggoheaven.domain.keyword.repository;

import java.util.List;

import com.example.junggoheaven.global.message.dto.MatchedUserDto;

public interface KeywordCustomRepository {
	List<MatchedUserDto>  findAllUserIdByProductName(String productName);
}
