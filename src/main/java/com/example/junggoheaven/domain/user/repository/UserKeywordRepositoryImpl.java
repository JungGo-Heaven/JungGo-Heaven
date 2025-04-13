package com.example.junggoheaven.domain.user.repository;

import static com.example.junggoheaven.domain.user.entity.QUser.*;
import static com.example.junggoheaven.domain.user.entity.QUserKeyword.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserKeywordRepositoryImpl implements UserKeywordCustomRepository{
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Long> findAllUserIdByProductName(String productName) {

		return jpaQueryFactory.select(user.id)
			.from(user)
			.leftJoin(userKeyword)
			.on(user.id.eq(userKeyword.user.id))
			.where(
				notEqualStatus(UserStatus.DELETED.name()),
				Expressions.booleanTemplate(
					"{0} like concat('%', {1}, '%')",
					Expressions.constant(productName),
					userKeyword.keyword
				)
			)
			.distinct()
			.fetch();
	}

	@Override
	public List<User> findAllNonDeletedUserByProductName(String productName) {
		return jpaQueryFactory.selectFrom(user)
			.leftJoin(userKeyword)
			.on(user.id.eq(userKeyword.user.id))
			.where(
				notEqualStatus(UserStatus.DELETED.name()),
				Expressions.booleanTemplate(
					"{0} like concat('%', {1}, '%')",
					Expressions.constant(productName),
					userKeyword.keyword
				)
			)
			.distinct()
			.fetch();
	}

	private BooleanExpression notEqualStatus(String status) {
		return StringUtils.isBlank(status) ? null : user.status.ne(UserStatus.of(status));
	}
}
