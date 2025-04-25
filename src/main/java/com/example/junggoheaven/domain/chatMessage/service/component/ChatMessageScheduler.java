package com.example.junggoheaven.domain.chatMessage.service.component;

import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatMessage.redis.dto.RedisChatMessageDto;
import com.example.junggoheaven.domain.chatMessage.service.ChatMessageService;
import com.example.junggoheaven.domain.image.service.ChatRoomImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageScheduler {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomImageService chatRoomImageService;

    @Scheduled(cron = "30 55 22 * * *")
    public void syncChatMessagesToDb() {
        // 분산 락 획득
        Boolean acquired = redisTemplate.opsForValue()
                .setIfAbsent("lock:chat-messages", "1", java.time.Duration.ofMinutes(10));

        if (!Boolean.TRUE.equals(acquired)) {
            return;
        }

        try {
            log.info("채팅 메시지 동기화 작업 시작");
            List<RedisChatMessageDto> batchText = new ArrayList<>();
            List<RedisChatMessageDto> batchImage = new ArrayList<>();

            long now = System.currentTimeMillis();
            long threshold = now - 1000L * 60; // 7일 전

            ScanOptions scanOptions = ScanOptions.scanOptions().match("chat:chatRoom:*").count(100).build();
            try (Cursor<byte[]> cursor = redisTemplate.getRequiredConnectionFactory().getConnection().scan(scanOptions)) {
                while (cursor.hasNext()) {
                    String key = new String(cursor.next(), StandardCharsets.UTF_8);
                    Set<Object> expired = redisTemplate.opsForZSet().rangeByScore(key, 0, threshold);

                    if (expired == null || expired.isEmpty()) continue;

                    for (Object item : expired) {
                        try {
                            RedisChatMessageDto dto = (RedisChatMessageDto) item;
                            if(dto.getMessageType().equals(MessageType.TEXT)){
                                batchText.add(dto);
                            } else {
                                batchImage.add(dto);
                            }

                        } catch (Exception e) {
                            log.error("채팅메세지 동기화 중 ZSet 항목 처리 오류 발생: {}, item: {}", e.getMessage(), item.toString());
                        }
                    }
                }
            }
            if (!batchText.isEmpty()) {
                chatMessageService.saveAll(batchText);
                for (RedisChatMessageDto dto : batchText) {
                    String key = "chat:chatRoom:" + dto.getChatRoomId();
                    redisTemplate.opsForZSet().remove(key, dto);
                }
            }
            if(!batchImage.isEmpty()){
                chatRoomImageService.saveAll(batchImage);
                for (RedisChatMessageDto dto : batchImage) {
                    String key = "chat:chatRoom:" + dto.getChatRoomId();
                    redisTemplate.opsForZSet().remove(key, dto);
                }
            }
            log.info("채팅 메시지 동기화 작업 완료");
        } catch (Exception e) {
            log.error("채팅메세지 동기화 중 오류 발생: {}", e.getMessage());
        }  finally {
            // 락 해제
            redisTemplate.delete("lock:chat-messages");
        }
    }
}
