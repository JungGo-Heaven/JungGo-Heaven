package com.example.junggoheaven.global.message.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.domain.keyword.repository.KeywordRepositoryImpl;
import com.example.junggoheaven.global.message.dto.MatchedUserDto;
import com.example.junggoheaven.global.message.event.finder.ProductRegisteredEvent;
import com.example.junggoheaven.global.message.event.mapper.ChannelMappingEvent;
import com.example.junggoheaven.global.message.publisher.EventPublisher;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.ScrollResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ESKeywordMatchedUserFinder {
	private final ElasticsearchClient esClient;
	private final KeywordRepositoryImpl keywordRepository;
	private final EventPublisher eventPublisher;

	/*
	 * 상품 게시글 등록시 해당 게시글 이름에 포함된 Keyword 를 등록한 User 를 선별하는 Component
	 * 선별 후 채널을 분류하는 ChannelMapper 를 호출한다.
	 * ElasticSearch 에 Bool Query 를 통해서 선별한다.
	 * */

	@Async
	@EventListener
	public void findKeywordMatchedUser(ProductRegisteredEvent event) {
		String name = event.getNotificationMessage();

		List<MatchedUserDto> userList = keywordRepository.findAllUserIdByProductName(name);

		eventPublisher.publishEvent(
			new ChannelMappingEvent(event.getSource(),
				event.getUserId(),
				event.getNotificationType(),
				event.getNotificationMessage(),
				userList
			));
	}
}
