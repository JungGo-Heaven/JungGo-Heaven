package com.example.junggoheaven.domain.user.service.component;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.exception.EmailNotFoundException;
import com.example.junggoheaven.domain.user.exception.UserNotFoundException;
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

	public Boolean existsByUserId(Long userId){
		return userRepository.existsById(userId);
	}

	public User findByUserEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
	}

	public User findByUserId(Long id) {
		return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
	}

}
