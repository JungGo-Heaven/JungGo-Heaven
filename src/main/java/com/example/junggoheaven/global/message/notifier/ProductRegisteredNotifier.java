package com.example.junggoheaven.global.message.notifier;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.repository.UserKeywordRepository;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.message.event.ProductRegisteredEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// todo: 동기 vs 비동기 차이 확인
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductRegisteredNotifier {
	private final UserKeywordRepository userKeywordRepository;
	private final UserFinder userFinder;

	@Async
	@EventListener
	public void processProductRegisteredEvent(ProductRegisteredEvent event) {
		String name = event.getProductName();

		long startedAt = System.currentTimeMillis();
		// List<Long> userIds = userKeywordRepository.findAllUserIdByProductName(name);
		List<User> userList = userKeywordRepository.findAllNonDeletedUserByProductName(name);

		// 선별된 사용자 조회
		// long startedAt = System.currentTimeMillis();

		// JPA findById = 3 m 18.72 s
		// 173124 ms
		// List<User> userList = userIds.stream()
		// 	.map(userFinder::findByUserId)
		// 	.toList();

		// @Query = 26.03 s
		// List<User> userList = userIds.stream()
		// 	.map(userFinder::findNonDeletedUserById)
		// 	.toList();

		log.info("findByUserId: {}", System.currentTimeMillis() - startedAt);
		log.info("Total Users are {}", userList.size());
	}
}
