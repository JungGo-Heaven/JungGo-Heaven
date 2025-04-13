package com.example.junggoheaven.domain.user.repository;

import java.util.List;

import com.example.junggoheaven.domain.user.entity.User;

public interface UserKeywordCustomRepository {
	List<Long> findAllUserIdByProductName(String productName);
	List<User> findAllNonDeletedUserByProductName(String productName);
}
