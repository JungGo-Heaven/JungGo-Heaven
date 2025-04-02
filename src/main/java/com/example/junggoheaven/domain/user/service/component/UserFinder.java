package com.example.junggoheaven.domain.user.service.component;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class UserFinder {

	private final UserRepository userRepository;

	// TODO: 추후 예외 수정 예정
	public User findByUserId(Long id){
		return userRepository.findById(id).orElseThrow(RuntimeException::new);
	}

	public User FindByUserEmail(String email){
		return userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
	}
}
