package com.example.junggoheaven.domain.user.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserBulkRepository {
	private final JdbcTemplate jdbcTemplate;

	private static final int BATCH_SIZE = 1000;

	public void bulkInsert(List<User> users) {
		String sql = "INSERT INTO users (email, name, phone_number, role, status) VALUES (?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, users, BATCH_SIZE, (ps, user) -> {
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPhoneNumber());
			ps.setString(4, user.getRole().name());
			ps.setString(5, user.getStatus().name());
		});
	}
}
