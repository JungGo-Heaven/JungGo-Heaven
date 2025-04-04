package com.example.junggoheaven.domain.user.service.component;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.domain.user.exception.AlreadyDeletedUserException;
import com.example.junggoheaven.domain.user.exception.EmailNotFoundException;
import com.example.junggoheaven.domain.user.exception.UserNotFoundException;
import com.example.junggoheaven.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserFinderTest {

	@InjectMocks
	private UserFinder userFinder;

	@Mock
	private UserRepository userRepository;

	User user;
	User deletedUser;
	Long userId = 1L;
	Long deletedUserId = 2L;
	String email = "test@test.com";

	@BeforeEach
	void setUp() {
		user = new User(email, "password", "name", "phoneNumber", "address");
		ReflectionTestUtils.setField(user, "id", userId);
		ReflectionTestUtils.setField(user, "createdAt", LocalDateTime.now());
		ReflectionTestUtils.setField(user, "modifiedAt", LocalDateTime.now());

		deletedUser = new User(email, "password", "name", "phoneNumber", "address");
		ReflectionTestUtils.setField(deletedUser, "id", deletedUserId);
		ReflectionTestUtils.setField(deletedUser, "createdAt", LocalDateTime.now());
		ReflectionTestUtils.setField(deletedUser, "modifiedAt", LocalDateTime.now());
		ReflectionTestUtils.setField(deletedUser, "deletedAt", LocalDateTime.now());
		ReflectionTestUtils.setField(deletedUser, "status", UserStatus.DELETED);
	}

	@Test
	void findByUserEmailOpt() {
		String badEmail = "non@test.com";
		given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

		Optional<User> good = userFinder.findByUserEmailOpt(email);
		Optional<User> bad = userFinder.findByUserEmailOpt(badEmail);

		assertThat(good.isPresent()).isTrue();
		assertThat(bad.isPresent()).isFalse();
	}

	@Test
	void findByUserId() {
		given(userRepository.findById(userId)).willReturn(Optional.of(user));
		User getUser = userFinder.findByUserId(userId);

		assertThat(getUser).isNotNull();
		assertThat(getUser.getId()).isEqualTo(userId);
	}

	@Test
	void findByUserId_실패() {
		Long badId = 2L;
		given(userRepository.findById(badId)).willReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> {
			userFinder.findByUserId(badId);
		});
	}

	@Test
	void findByUserEmail() {
		given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
		User getUser = userFinder.findByUserEmail(email);

		assertThat(getUser).isNotNull();
		assertThat(getUser.getId()).isEqualTo(userId);
	}

	@Test
	void findByUserEmail_실패() {
		String badEmail = "non@test.com";
		given(userRepository.findByEmail(badEmail)).willReturn(Optional.empty());

		assertThrows(EmailNotFoundException.class, () -> {
			userFinder.findByUserEmail(badEmail);
		});
	}

	@Test
	void findNonDeletedByUserId() {
		given(userRepository.findByIdAndNonDeleted(userId)).willReturn(Optional.of(user));

		User getUser = userFinder.findNonDeletedByUserId(userId);
		assertThat(getUser).isNotNull();
		assertThat(getUser.getId()).isEqualTo(userId);
	}

	@Test
	void findNonDeletedByUserId_탈퇴한_유저_비교() {
		given(userRepository.findByIdAndNonDeleted(deletedUserId)).willReturn(Optional.empty());
		given(userRepository.findByIdAndNonDeleted(userId)).willReturn(Optional.of(user));

		User good = userFinder.findNonDeletedByUserId(userId);
		assertThat(good).isNotNull();
		assertThat(good.getId()).isEqualTo(userId);

		assertThrows(AlreadyDeletedUserException.class, () -> {
			userFinder.findNonDeletedByUserId(deletedUserId);
		});
	}
}