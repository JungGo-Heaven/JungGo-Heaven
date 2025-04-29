// package com.example.junggoheaven.domain.keyword.bulk;
//
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;
// import java.util.UUID;
// import java.util.stream.LongStream;
//
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.util.ReflectionTestUtils;
//
// import com.example.junggoheaven.domain.keyword.entity.KeywordDocument;
// import com.example.junggoheaven.domain.keyword.repository.KeywordBulkRepository;
// import com.example.junggoheaven.domain.keyword.repository.KeywordDocumentRepository;
// import com.example.junggoheaven.domain.user.entity.User;
// import com.example.junggoheaven.domain.keyword.entity.UserKeyword;
// import com.example.junggoheaven.domain.user.repository.UserBulkRepository;
// import com.example.junggoheaven.domain.user.repository.UserKeywordBulkRepository;
// import com.example.junggoheaven.domain.user.repository.UserKeywordRepository;
// import com.example.junggoheaven.domain.user.repository.UserRepository;
// import com.example.junggoheaven.global.message.entity.NotificationChannel;
// import com.example.junggoheaven.global.message.enums.ChannelType;
// import com.example.junggoheaven.global.message.repository.NotificationChannelBulkRepository;
//
// import co.elastic.clients.elasticsearch.core.mget.MultiGetOperation;
//
// @SpringBootTest
// @ActiveProfiles("test")
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// class KeywordBulkInsertTest {
//
// 	@Autowired
// 	private UserBulkRepository userBulkRepository;
// 	@Autowired
// 	private UserKeywordBulkRepository userKeywordBulkRepository;
// 	@Autowired
// 	private NotificationChannelBulkRepository notificationChannelBulkRepository;
// 	@Autowired
// 	private UserRepository userRepository;
// 	@Autowired
// 	private KeywordBulkRepository keywordBulkRepository;
//
// 	private static final int TOTAL_USERS = 10_000;
// 	private static final int BATCH_SIZE = 1000;
// 	private static final String[] KEYWORDS = {
// 		"강아지", "고양이", "아이폰 3", "아이폰11", "책상", "의자", "냉장고", "나이키 신발", "아디다스 가방", "크록스"
// 	};
// 	private static final String[] CHANNELS = {
// 		ChannelType.KAKAO_TALK.name(), ChannelType.EMAIL.name(), ChannelType.WEB_PUSH.name(), ChannelType.FCM.name()
// 	};
//
//
// 	@Order(0)
// 	@Test
// 	public void bulkInsertUserTest() {
// 		Random random = new Random();
//
// 		for (int i = 0; i < TOTAL_USERS; i += BATCH_SIZE) {
// 			List<User> users = new ArrayList<>();
// 			Long startId = (long)i + 1;
// 			Long endId = (long)i + BATCH_SIZE;
//
//
// 			for (int j = 0; j < BATCH_SIZE; j++) {
// 				String email = "testUser" + (i + j) + "@example.com";
// 				String name = "name" + random.nextInt(TOTAL_USERS);
// 				String phoneNumber = "" + random.nextInt(TOTAL_USERS);
//
// 				User user = User.builder().email(email).name(name).phoneNumber(phoneNumber).build();
// 				users.add(user);
// 			}
//
// 			userBulkRepository.bulkInsert(users);
// 			List<User> savedUsers = userRepository.findUsersByIdBetween(startId, endId);
// 			keywordBulkRepository.bulkInsertUsers(savedUsers);
// 		}
// 	}
//
// 	@Order(1)
// 	@Test
// 	void bulkInsertUserKeywordsTest() {
// 		Random random = new Random();
//
// 		for (int i = 0; i < TOTAL_USERS; i += BATCH_SIZE / 10) {
// 			int userIndex = i - 1;
// 			Long startId = (long)i + 1;
// 			Long endId = (long)i + (BATCH_SIZE / 10);
//
// 			List<UserKeyword> userKeywords = new ArrayList<>();
// 			List<User> users = userRepository.findUsersByIdBetween(startId, endId);
// 			List<MultiGetOperation> kds = makeKeywordDocumentsByIdBetween(startId, endId);
// 			List<KeywordDocument> keywordDocuments = keywordBulkRepository.findKeywordDocumentsByIdBetween(kds);
// 			for (int j = 0; j < BATCH_SIZE; j++) {
// 				if (j % KEYWORDS.length == 0) {
// 					userIndex++;
// 				}
//
// 				String keyword = KEYWORDS[random.nextInt(KEYWORDS.length)];
//
// 				UserKeyword userKeyword = UserKeyword.of(keyword, users.get(userIndex % (BATCH_SIZE / 10)));
// 				userKeywords.add(userKeyword);
//
// 				keywordDocuments.get(userIndex % (BATCH_SIZE / 10)).addKeyword(KeywordDocument.Keyword.of(keyword));
// 			}
// 			keywordBulkRepository.bulkInsert(keywordDocuments);
// 			userKeywordBulkRepository.bulkInsert(userKeywords);
// 		}
// 	}
//
// 	List<MultiGetOperation> makeKeywordDocumentsByIdBetween(long startId, long endId) {
// 		return LongStream.rangeClosed(startId, endId)
// 			.mapToObj(i -> new MultiGetOperation.Builder()
// 				.id(String.valueOf(i))
// 				.build()).toList();
// 	}
//
// 	@Order(2)
// 	@Test
// 	void bulkInsertUserChannelsTest() {
// 		for (int i = 0; i < TOTAL_USERS; i += (BATCH_SIZE / 25) * 10) {
// 			int userIndex = i - 1;
// 			Long startId = (long)i + 1;
// 			Long endId = (long)i + (BATCH_SIZE / 25) * 10;
//
// 			List<NotificationChannel> channels = new ArrayList<>();
// 			List<User> users = userRepository.findUsersByIdBetween(startId, endId);
// 			List<MultiGetOperation> kds = makeKeywordDocumentsByIdBetween(startId, endId);
// 			List<KeywordDocument> keywordDocuments = keywordBulkRepository.findKeywordDocumentsByIdBetween(kds);
// 			for (int j = 0; j < (BATCH_SIZE / 25) * 10; j++) {
// 				userIndex++;
//
// 				for (int l = 0; l <= j % CHANNELS.length; l++) {
// 					NotificationChannel nc = NotificationChannel.of
// 						(ChannelType.values()[l], "token", users.get(userIndex % ((BATCH_SIZE / 25) * 10)));
// 					channels.add(nc);
//
// 					keywordDocuments.get(userIndex % ((BATCH_SIZE / 25) * 10)).addChannel(ChannelType.values()[l], "token");
// 				}
// 			}
// 			keywordBulkRepository.bulkInsert(keywordDocuments);
// 			notificationChannelBulkRepository.insert(channels);
// 		}
// 	}
// }