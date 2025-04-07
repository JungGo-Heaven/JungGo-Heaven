package com.example.junggoheaven.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserStatus;

public interface UserRepository extends JpaRepository<User,Long>, UserCustomRepository {
	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.id = :userId AND u.status <> 'DELETED'")
	Optional<User> findByIdAndNonDeleted(Long userId);
}
