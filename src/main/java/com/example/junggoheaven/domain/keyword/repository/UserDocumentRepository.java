package com.example.junggoheaven.domain.keyword.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.junggoheaven.domain.keyword.entity.UserDocument;

public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, String> {
}
