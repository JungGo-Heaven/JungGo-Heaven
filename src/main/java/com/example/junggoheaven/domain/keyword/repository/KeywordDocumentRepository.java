package com.example.junggoheaven.domain.keyword.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;

public interface KeywordDocumentRepository extends ElasticsearchRepository<KeywordDocument, String> {
}
