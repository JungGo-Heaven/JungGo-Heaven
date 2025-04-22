package com.example.junggoheaven.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.junggoheaven.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User,Long>, UserCustomRepository {
	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	List<User> findUsersByIdBetween(Long startId, Long endId);

	Optional<User> findByCustomerKey(String customerKey);
}
