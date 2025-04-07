package com.example.junggoheaven.domain.user.repository;

import static com.example.junggoheaven.domain.user.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<User> findAllByStatusAndEmail(String status, String email, Pageable pageable) {

		List<User> users = jpaQueryFactory
			.selectFrom(user)
			.where(
				equalStatus(status),
				containsEmail(email)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long size = jpaQueryFactory.select(user.id.countDistinct())
			.from(user)
			.where(
				equalStatus(status),
				containsEmail(email)
			)
			.fetchOne();

		return new PageImpl<>(users, pageable, size);
	}

	private BooleanExpression equalStatus(String status) {
		return StringUtils.isBlank(status) ? null : user.status.stringValue().contains(status);
	}

	private BooleanExpression containsEmail(String email) {
		return StringUtils.isBlank(email) ? null : user.email.contains(email);
	}
}
