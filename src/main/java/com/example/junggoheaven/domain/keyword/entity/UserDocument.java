package com.example.junggoheaven.domain.keyword.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.message.enums.ChannelType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(indexName = "users")
@Setting
@Mapping(mappingPath = "elastic/users-mapping.json")
@NoArgsConstructor
public class UserDocument {
	@Id
	private String id;

	// type: text, tokenizer 고려 (email 을 기반으로 조회할수도있음)
	@Field(type = FieldType.Text)
	private String email;


	// keyword 저장 개수는 최대 10, 하나의 keyword 당 길이는 최대 20
	@Field(type = FieldType.Nested)
	private List<Keyword> keywords = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Field(type = FieldType.Keyword)
	private List<ChannelType> channels = new ArrayList<> ();

	private UserDocument (String id, String email) {
		this.id = id;
		this.email = email;
	}

	public static UserDocument of (User user) {
		return new UserDocument(user.getId().toString(), user.getEmail());
	}

	public void addKeyword(Keyword keyword) {
		keywords.add(keyword);
	}

	public void deleteKeyword(String keyword) {
		keywords.removeIf(k -> k.getKeyword().equals(keyword));
	}

	public void addChannel(ChannelType channel) {
		channels.add(channel);
	}

	public void deleteChannel(ChannelType channel) {
		channels.remove(channel);
	}

	@Getter
	@NoArgsConstructor
	public static class Keyword {
		@Field(type = FieldType.Keyword)
		private String keyword;

		@Field(type = FieldType.Keyword)
		private List<String> excludeKeywords = new ArrayList<>();

		private Keyword(String keyword) {
			this.keyword = keyword;
		}

		public static Keyword of (String keyword) {
			return new Keyword(keyword);
		}

		public void addExcludeKeywords(String keyword) {
			this.excludeKeywords.add(keyword);
		}

		public void deleteExcludeKeyword(String excludeKeyword) {
			this.excludeKeywords.removeIf(ek -> ek.equals(excludeKeyword));
		}
	}
}
