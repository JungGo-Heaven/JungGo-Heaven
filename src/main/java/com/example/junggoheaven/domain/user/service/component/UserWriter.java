package com.example.junggoheaven.domain.user.service.component;

import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserWriter {

	private final UserRepository userRepository;

	public User saveUser(User user){
		return userRepository.save(user);
	}
}
