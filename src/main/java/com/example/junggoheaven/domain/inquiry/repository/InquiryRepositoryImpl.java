package com.example.junggoheaven.domain.inquiry.repository;

import static com.example.junggoheaven.domain.inquiry.entity.QInquiry.*;
import static com.example.junggoheaven.domain.user.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.inquiry.eunms.InquiryStatus;
import com.example.junggoheaven.domain.user.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InquiryRepositoryImpl implements InquiryCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<Inquiry> findAllByForAdmin(String title, String status, String writer, Pageable pageable) {
		QUser respondentUser = new QUser("respondent");

		List<Inquiry> inquiries = jpaQueryFactory
			.selectFrom(inquiry)
			.leftJoin(inquiry.writer, user).fetchJoin()
			.leftJoin(inquiry.respondent, respondentUser).fetchJoin()
			.where(
				containsTitle(title),
				equalStatus(status),
				containsWriter(writer)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long size = jpaQueryFactory.select(inquiry.id.countDistinct())
			.from(inquiry)
			.where(
				containsTitle(title),
				equalStatus(status),
				containsWriter(writer)
			)
			.fetchOne();

		return new PageImpl<>(inquiries, pageable, size);
	}

	private BooleanExpression containsTitle(String title) {
		return StringUtils.isBlank(title) ? null : inquiry.title.contains(title);
	}

	private BooleanExpression equalStatus(String status) {
		return StringUtils.isBlank(status) ? null : inquiry.status.eq(InquiryStatus.of(status));
	}

	private BooleanExpression containsWriter(String email) {
		return StringUtils.isBlank(email) ? null : inquiry.writer.email.contains(email);
	}
}
