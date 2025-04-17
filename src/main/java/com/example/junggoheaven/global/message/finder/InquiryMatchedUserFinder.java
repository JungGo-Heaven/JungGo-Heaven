package com.example.junggoheaven.global.message.finder;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.message.event.finder.InquiryResponseRegisteredEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InquiryMatchedUserFinder {
	private final UserFinder userFinder;

	@EventListener
	public void findInquiryResponseMatchedUser(InquiryResponseRegisteredEvent event) {
		Long userId = event.getUserId();

		User user = userFinder.findByUserId(userId);
		log.info("{}님, 문의에 대한 답변이 등록되었습니다.", user.getName());
	}
}
