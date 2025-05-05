package com.example.junggoheaven.domain.keyword.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.global.message.dto.MatchedUserDto;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.ScrollResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KeywordRepositoryImpl implements KeywordCustomRepository {
	private final ElasticsearchClient esClient;

	@Override
	public List<MatchedUserDto> findAllUserIdByProductName(String productName) {
		/*
		 * Bool Query 에서 Must 부분을 담당한다.
		 * 사용자가 등록한 키워드와 일치 할 경우 선별한다.
		 * */

		Query must = MatchQuery.of(m -> m
				.field("keywords.keyword")
				.query(productName))
			._toQuery();

		/*
		 * Bool Query 에서 Must_Not 을 담당한다.
		 * 사용자가 등록한 키워드에서 제외키워드와 일치 할 경우 선별 대상에서 제외한다.
		 * */

		Query mustNot = MatchQuery.of(m -> m
				.field("keywords.excludeKeywords")
				.query(productName))
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

				if (scrollResponse.hits().hits().isEmpty())
					break;
			}

			String finalScrollId1 = scrollId;
			esClient.clearScroll(c -> c.scrollId(finalScrollId1));

			return userList;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ArrayList<>();
	}
}
