package com.example.junggoheaven.domain.user.service.component;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@RequiredArgsConstructor
public class UserChecker {

	private final UserRepository userRepository;

	public Boolean existsByUserEmail(String email){
		return userRepository.existsByEmail(email);
	}

	public Boolean existsByUserId(Long userId){
		return userRepository.existsById(userId);
	}
}
