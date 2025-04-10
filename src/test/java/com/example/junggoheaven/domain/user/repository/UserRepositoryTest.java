package com.example.junggoheaven.domain.user.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.config.QueryDslConfig;

@DataJpaTest
@Import(QueryDslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	User user;
	User deletedUser;
	Pageable pageable;

	@BeforeEach
	void setUp() {
		user = new User("test@test.com", "password", "name", "phoneNumber", "address");
		deletedUser = new User("test2@test.com", "password", "name", "phoneNumber", "address");
		pageable = PageRequest.of(0, 5);

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

	@Test
	void findAllByStatusAndEmail(){
		String findEmail = "test";
		String findStatus = "DELETED";

		Page<User> emailAndStatus = userRepository.findAllByStatusAndEmail(findStatus, findEmail, pageable);
		Page<User> onlyStatus = userRepository.findAllByStatusAndEmail(findStatus, null, pageable);
		Page<User> onlyEmail = userRepository.findAllByStatusAndEmail(null, findEmail, pageable);

		assertThat(emailAndStatus.getTotalElements()).isEqualTo(1);
		assertThat(onlyStatus.getTotalElements()).isEqualTo(1);
		assertThat(onlyEmail.getTotalElements()).isEqualTo(2);
	}
}