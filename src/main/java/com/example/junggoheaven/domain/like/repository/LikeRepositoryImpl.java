package com.example.junggoheaven.domain.like.repository;

import static com.example.junggoheaven.domain.like.entity.QLike.*;
import static com.example.junggoheaven.domain.product.entity.QProduct.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.like.entity.Like;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Like> findTop5Likes() {
		LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

		List<Long> top5 = jpaQueryFactory
			.select(like.product.id)
			.from(like)
			.join(like.product, product)
			.where(product.createdAt.after(sevenDaysAgo)
				.and(product.deletedAt.isNull()))
			.groupBy(like.product.id)
			.orderBy(like.product.count().desc())
			.limit(5)
			.fetch();

		return jpaQueryFactory
			.selectFrom(like)
			.join(like.product, product).fetchJoin()
			.where(like.product.id.in(top5))
			.fetch();
	}
}
