package com.example.junggoheaven.domain.user.service.component;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.domain.user.exception.AlreadyDeletedUserException;
import com.example.junggoheaven.domain.user.exception.EmailNotFoundException;
import com.example.junggoheaven.domain.user.exception.UserNotFoundException;
import com.example.junggoheaven.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@RequiredArgsConstructor
public class UserFinder {

	private final UserRepository userRepository;

	public Optional<User> findByUserEmailOpt(String email) {
		return userRepository.findByEmail(email);
	}

	public User findByUserId(Long id) {
		return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
	}

	public User findByUserEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
	}

	public User findValidUserById(Long userId) {
		User user = findByUserId(userId);
		if (user.getStatus().equals(UserStatus.DELETED)) {
			throw new AlreadyDeletedUserException();
		}
		return user;
	}

	public User findNonDeletedUserById(Long userId) {
		return userRepository.findByIdAndNonDeleted(userId).orElseThrow(AlreadyDeletedUserException::new);
	}

	public Page<User> findUsersForAdmin(String status, String email, Pageable pageable) {
		return userRepository.findAllByStatusAndEmail(status, email, pageable);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}
}
