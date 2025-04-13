package com.example.junggoheaven.domain.user.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.user.entity.UserKeyword;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserKeywordBulkRepository {
	private final JdbcTemplate jdbcTemplate;

	private static final int BATCH_SIZE = 1000;

	public void bulkInsert(List<UserKeyword> userKeywords) {
		String sql = "INSERT INTO user_keyword (keyword, user_id) VALUES (?, ?)";

		jdbcTemplate.batchUpdate(sql, userKeywords, BATCH_SIZE, (ps, userKeyword) -> {
			ps.setString(1, userKeyword.getKeyword());
			ps.setLong(2, userKeyword.getUser().getId());
		});
	}
}



