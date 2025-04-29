package com.example.junggoheaven.global.message.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
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
	private final EventPublisher eventPublisher;

	/*
	 * 상품 게시글 등록시 해당 게시글 이름에 포함된 Keyword 를 등록한 User 를 선별하는 Component
	 * 선별 후 채널을 분류하는 ChannelMapper 를 호출한다.
	 * ElasticSearch 에 Term Query 를 통해서 선별한다.
	 * */

	@Async
	@EventListener
	public void findKeywordMatchedUser(ProductRegisteredEvent event) {
		long startedAt = System.currentTimeMillis();
		String name = event.getNotificationMessage();

		/*
		* Bool Query 에서 Must 부분을 담당한다.
		* 사용자가 등록한 키워드와 일치 할 경우 선별한다.
		* */

		Query must = MatchQuery.of(m -> m
			.field("keywords.keyword")
			.query(name))
			._toQuery();

		/*
		* Bool Query 에서 Must_Not 을 담당한다.
		* 사용자가 등록한 키워드에서 제외키워드와 일치 할 경우 선별 대상에서 제외한다.
		* */

		Query mustNot = MatchQuery.of(m -> m
			.field("keywords.excludeKeywords")
			.query(name))
			._toQuery();

		try {
			SearchResponse<KeywordDocument> response = esClient.search(s -> s
				.index("keyword")
					.scroll(t -> t.time("1m"))
					.size(10000)
					.query(q -> q
						.nested(n -> n
							.path("keywords")
							.query(nq -> nq
								.bool(b -> b
									.must(must)
									.mustNot(mustNot))
							)
						)
					),
				KeywordDocument.class
			);

			/*
			* ElasticSearch 는 한번에 최대 10000개의 Document 만 Search 가능하다.
			* 따라서 대상 Document 가 10000개를 초과 할 경우 scrollId 를 기억해서 반복 요청을 보낸다.
			* */

			String scrollId = response.scrollId();

			List<Hit<KeywordDocument>> hits = response.hits().hits();
			List<MatchedUserDto> userList = new ArrayList<>(hits.stream()
				.map(Hit::source)
				.filter(Objects::nonNull)
				.map(MatchedUserDto::of)
				.toList());

			while (true) {
				String finalScrollId = scrollId;
				ScrollResponse<KeywordDocument> scrollResponse = esClient.scroll(s -> s
						.scrollId(finalScrollId)
						.scroll(t -> t.time("1m")),
					KeywordDocument.class);

				scrollId = scrollResponse.scrollId();

				userList.addAll(scrollResponse.hits().hits().stream()
					.map(Hit::source)
					.filter(Objects::nonNull)
					.map(MatchedUserDto::of)
					.toList());

				if (scrollResponse.hits().hits().isEmpty()) break;
			}

			String finalScrollId1 = scrollId;
			esClient.clearScroll(c -> c.scrollId(finalScrollId1));

			log.info("ElasticSearch End: {}", System.currentTimeMillis() - startedAt);
			log.info("Total Users by ES are {}", userList.size());
			eventPublisher.publishEvent(
				new ChannelMappingEvent(event.getSource(),
					event.getUserId(),
					event.getNotificationType(),
					event.getNotificationMessage(),
					userList
				));
			log.info("ESKeywordMatchedUserFinder Done");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
