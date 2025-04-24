// package com.example.junggoheaven.domain.user.service;
//
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;
// import java.util.UUID;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.TestPropertySource;
//
// import com.example.junggoheaven.domain.user.entity.User;
// import com.example.junggoheaven.domain.keyword.entity.UserKeyword;
// import com.example.junggoheaven.domain.user.repository.UserBulkRepository;
// import com.example.junggoheaven.domain.user.repository.UserKeywordBulkRepository;
// import com.example.junggoheaven.domain.user.repository.UserRepository;
// import com.example.junggoheaven.global.message.entity.NotificationChannel;
// import com.example.junggoheaven.global.message.enums.ChannelType;
// import com.example.junggoheaven.global.message.repository.NotificationChannelBulkRepository;
//
// @SpringBootTest
// @ActiveProfiles("test")
// class UserKeywordServiceTest {
//
// 	@Autowired
// 	private UserBulkRepository userBulkRepository;
// 	@Autowired
// 	private UserKeywordBulkRepository userKeywordBulkRepository;
// 	@Autowired
// 	private NotificationChannelBulkRepository notificationChannelBulkRepository;
// 	@Autowired
// 	private UserRepository userRepository;
//
// 	private static final int TOTAL_USERS = 1_000_000;
// 	private static final int BATCH_SIZE = 1000;
// 	private static final String[] KEYWORDS = {
// 		"강아지", "고양이", "아이폰 3", "아이폰11", "책상", "의자", "냉장고", "나이키 신발", "아디다스 가방", "크록스"
// 	};
// 	private static final String[] CHANNELS = {
// 		"카카오톡", "Wep Push", "FCM", "EMAIL"
// 	};
//
// 	@Test
// 	public void bulkInsertUserTest() {
// 		Random random = new Random();
//
// 		for (int i = 0; i < TOTAL_USERS; i += BATCH_SIZE) {
// 			List<User> users = new ArrayList<>();
//
// 			for (int j = 0; j < BATCH_SIZE; j++) {
// 				String email = "user" + UUID.randomUUID() + "@example.com";
// 				String name = "name" + random.nextInt(1000000);
// 				String phoneNumber = "" + random.nextInt(1000000);
//
// 				User user = new User(email, name, phoneNumber);
// 				users.add(user);
// 			}
//
// 			userBulkRepository.bulkInsert(users);
// 		}
// 	}
//
// 	@Test
// 	void bulkInsertUserKeywordsTest() {
// 		Random random = new Random();
//
// 		for (int i = 0; i < TOTAL_USERS; i += BATCH_SIZE / 10) {
// 			int userIndex = i - 1;
//
// 			List<UserKeyword> userKeywords = new ArrayList<>();
// 			List<User> users = userRepository.findUsersByIdBetween((long)i + 1, (long)i + 100L);
// 			for (int j = 0; j < BATCH_SIZE; j++) {
// 				if (j % KEYWORDS.length == 0) {
// 					userIndex++;
// 				}
//
// 				String keyword = KEYWORDS[random.nextInt(KEYWORDS.length)];
//
// 				UserKeyword userKeyword = UserKeyword.of(keyword, users.get(userIndex % 100));
// 				userKeywords.add(userKeyword);
// 			}
//
// 			userKeywordBulkRepository.bulkInsert(userKeywords);
// 		}
// 	}
//
// 	@Test
// 	void bulkInsertUserChannelsTest() {
// 		for (int i = 0; i < TOTAL_USERS / 10; i += BATCH_SIZE) {
// 			int userIndex = i - 1;
//
// 			List<NotificationChannel> channels = new ArrayList<>();
// 			List<User> users = userRepository.findUsersByIdBetween((long)i + 1, (long)i + 1000L);
// 			for (int j = 0; j < BATCH_SIZE; j++) {
// 				userIndex++;
//
// 				for (int l = 0; l <= j % CHANNELS.length; l++) {
// 					NotificationChannel nc = NotificationChannel
// 						.of(ChannelType.values()[l], users.get(userIndex % 1000));
// 					channels.add(nc);
// 				}
// 			}
// 			notificationChannelBulkRepository.insert(channels);
// 		}
// 	}
// }