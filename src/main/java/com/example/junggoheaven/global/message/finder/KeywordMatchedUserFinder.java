package com.example.junggoheaven.global.message.finder;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.user.repository.UserKeywordRepository;
import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.event.mapper.ChannelMappingEvent;
import com.example.junggoheaven.global.message.event.finder.ProductRegisteredEvent;
import com.example.junggoheaven.global.message.publisher.EventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeywordMatchedUserFinder {
	private final UserKeywordRepository userKeywordRepository;
	private final EventPublisher eventPublisher;

	/*
	* 상품 게시글 등록시 해당 게시글 이름에 포함된 Keyword 를 등록한 User 를 선별하는 Component
	* 선별 후 채널을 분류하는 ChannelMapper 를 호출한다.
	* Mysql DataBase 에서 Like Query 를 통해서 선별한다.
	* */

	@Deprecated
	@Async
	// @EventListener
	public void findKeywordMatchedUser(ProductRegisteredEvent event) {
		long startedAt = System.currentTimeMillis();
		String name = event.getNotificationMessage();

		List<MatchedUserDto> userList = userKeywordRepository.findAllUserIdByProductName(name);

		log.info("Mysql End: {}", System.currentTimeMillis() - startedAt);
		log.info("Total Users by Mysql are {}", userList.size());
		eventPublisher.publishEvent( new ChannelMappingEvent(event.getSource(), event.getUserId(), event.getNotificationType(), event.getNotificationMessage(), userList));
		log.info("KeywordMatchedUserFinder Done");
	}
}
