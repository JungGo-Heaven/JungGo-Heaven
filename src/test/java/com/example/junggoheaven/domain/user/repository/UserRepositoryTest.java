package com.example.junggoheaven.domain.user.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.domain.user.exception.AlreadyDeletedUserException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	User user;
	User deletedUser;

	@BeforeEach
	void setUp() {
		user = new User("test@test.com", "password", "name", "phoneNumber", "address");
		deletedUser = new User("test2@test.com", "password", "name", "phoneNumber", "address");

		user = userRepository.save(user);
		deletedUser = userRepository.save(deletedUser);

		deletedUser.deleteUser();
	}

	@Test
	void findByIdAndNonDeleted() {
		Optional<User> nonDeleted = userRepository.findByIdAndNonDeleted(user.getId());
		Optional<User> deleted = userRepository.findByIdAndNonDeleted(deletedUser.getId());

		assertThat(nonDeleted).isNotEmpty();
		assertThat(deleted).isEmpty();
	}
}