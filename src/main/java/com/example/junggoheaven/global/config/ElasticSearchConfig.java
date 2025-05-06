package com.example.junggoheaven.global.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.junggoheaven.domain.user.repository")
@RequiredArgsConstructor
public class ElasticSearchConfig {
	@Value("${spring.elasticsearch.uris}")
	private String uris;

	@Bean
	public ElasticsearchClient elasticsearchClient() {
		RestClient restClient = RestClient.builder(
			new HttpHost(uris, 9200, "http")
		).build();

		RestClientTransport transport = new RestClientTransport(
			restClient, new JacksonJsonpMapper()
		);

		return new ElasticsearchClient(transport);
	}
}
