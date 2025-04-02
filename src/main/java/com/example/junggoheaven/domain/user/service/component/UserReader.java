package com.example.junggoheaven.domain.user.service.component;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReader {

	private final UserRepository userRepository;

	public Boolean existsByUserEmail(String email){
		return userRepository.existsByEmail(email);
	}
}
