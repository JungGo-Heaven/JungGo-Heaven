package com.example.junggoheaven.domain.keyword.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
import com.example.junggoheaven.domain.keyword.entity.UserKeyword;
import com.example.junggoheaven.domain.user.entity.User;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.MgetResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.get.GetResult;
import co.elastic.clients.elasticsearch.core.mget.MultiGetOperation;
import co.elastic.clients.elasticsearch.core.mget.MultiGetResponseItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KeywordBulkRepository {
	private final ElasticsearchClient esClient;
	private BulkRequest.Builder br;

	public List<KeywordDocument> findKeywordDocumentsByIdBetween(List<MultiGetOperation> keywordDocuments) {
		try {
			MgetResponse<KeywordDocument> response = esClient.mget(mg -> mg.index("keyword")
					.docs(keywordDocuments),
				KeywordDocument.class);

			return response.docs().stream()
				.filter(item -> item.result().found())
				.map(item -> item.result().source())
				.collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void bulkInsertUsers(List<User> users) {
		List<KeywordDocument> keywordDocuments = users.stream().map(KeywordDocument::of).toList();

		bulk(keywordDocuments);
	}

	public void bulkInsert(List<KeywordDocument> keywordDocuments) {
		bulk(keywordDocuments);
	}

	private void bulk(List<KeywordDocument> keywordDocuments) {
		br = new BulkRequest.Builder();

		for (KeywordDocument keyword : keywordDocuments) {
			br.operations(op -> op
				.index(idx -> idx
					.index("keyword")
					.id(keyword.getId())
					.document(keyword)
				)
			);
		}

		try {
			BulkResponse result = esClient.bulk(br.build());

			if (result.errors()) {
				log.error("Bulk had errors");
				for (BulkResponseItem item: result.items()) {
					if (item.error() != null) {
						log.error(item.error().reason());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
