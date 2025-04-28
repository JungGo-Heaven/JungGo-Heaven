package com.example.junggoheaven.domain.user.repository;

import static com.example.junggoheaven.domain.keyword.entity.QUserKeyword.*;
import static com.example.junggoheaven.domain.user.entity.QUser.*;
import static com.example.junggoheaven.global.message.entity.QNotificationChannel.*;
import static com.querydsl.core.group.GroupBy.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.user.enums.UserStatus;
import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.dto.NotificationChannelDto;
import com.querydsl.core.types.Projections;
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
	public List<MatchedUserDto> findAllUserIdByProductName(String productName) {

		return jpaQueryFactory
			.from(user)
			.leftJoin(userKeyword)
			.on(user.id.eq(userKeyword.user.id))
			.leftJoin(notificationChannel)
			.on(user.id.eq(notificationChannel.user.id))
			.where(
				notEqualStatus(UserStatus.DELETED.name()),
				Expressions.booleanTemplate(
					"{0} like concat('%', {1}, '%')",
					Expressions.constant(productName),
					userKeyword.keyword
				)
			)
			.transform(
				groupBy(user.id).list(
					Projections.fields(
						MatchedUserDto.class,
						user.id.as("userId"),
						user.email,
						list(
							Projections.fields(
								NotificationChannelDto.class,
								notificationChannel.channelType,
								notificationChannel.token
							)
						).as("channels")
					)
				)
			);
	}

	@Override
	public MatchedUserDto findMatchedUserDtoById(Long userId) {
		return jpaQueryFactory
			.from(user)
			.leftJoin(notificationChannel)
			.on(user.id.eq(notificationChannel.user.id))
			.where(
				user.id.eq(userId)
			)
			.transform(
				groupBy(user.id).as(
					Projections.fields(
						MatchedUserDto.class,
						user.id.as("userId"),
						user.email,
						list(
							Projections.fields(
								NotificationChannelDto.class,
								notificationChannel.channelType,
								notificationChannel.token
							)
						).as("channels")
					)
				)
			)
			.get(userId);
	}

	private BooleanExpression notEqualStatus(String status) {
		return StringUtils.isBlank(status) ? null : user.status.ne(UserStatus.of(status));
	}
}
